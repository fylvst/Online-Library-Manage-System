<template>
  <div class="container">
    <div class="header">

      <el-tabs style="max-width: 99%" v-model="activeName" class="tabs" @tab-click="handleClick">
        <el-tab-pane label="Book List" name="0"></el-tab-pane>
        <el-tab-pane label="Lend Record List" name="1"></el-tab-pane>
        <el-tab-pane label="NotReadyYet" name="2"></el-tab-pane>
        <el-tab-pane label="NotReadyYet" name="3"></el-tab-pane>
      </el-tabs>

      <div class="information">
        <p class="welcome">Welcome,</p>
        <el-button class="nickname" type="text" @click="infoShowFunction"> {{userData.nickname}}</el-button>
        <el-button class="nickname" type="danger" link @click="logoutFun">logout</el-button>
      </div>
    </div>


    <div class="panelView" >
      <el-carousel ref="headerCarouselRef" class="carousel" height="700px" :autoplay="false" indicator-position="none"
                   arrow="never">
        <el-carousel-item height="1000px" :key="1">
          <patron_book-view_pannel v-model="bookPanelRef"></patron_book-view_pannel>
        </el-carousel-item>
        <el-carousel-item height="1000px"  :key="2">
          <patron_lend-record_panenel ref="lendRecordRef"></patron_lend-record_panenel>
        </el-carousel-item>
        <el-carousel-item :key="3">
          <h1>3</h1>
        </el-carousel-item>
        <el-carousel-item :key="4">
          <h1>4</h1>
        </el-carousel-item>
      </el-carousel>



    </div>





    <!--个人身份信息修改弹窗 START-->
    <base-user-information-change v-model="userData"></base-user-information-change>
    <!--个人身份信息修改弹窗 END-->


  </div>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from 'vue-router'
import {getCurrentInstance, ref} from "vue";
import axios from "axios";
import BaseUserInformationChange from "@/components/baseUserInformationChange.vue";
import {TabsPaneContext} from "element-plus";
import Patron_lendRecord_panenel from "@/components/patron_lendRecord_panenel.vue";
import Patron_bookView_pannel from "@/components/patron_bookView_pannel.vue";

const router = useRouter();
const route = useRoute();
const pid = ref(window.localStorage.getItem("pid"));
const nickname = ref("placeholder");
const userData = ref();
const bookPanelRef = ref({
  setup: false,
});
const lendRecordRef = ref();

const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取


axios({
  method: "POST",
  url: serverPath + "patron/getByPid",
  params: {
    pid: pid.value
  }
}).then(res => {
  userData.value = res.data.data;
  userData.value.pid = pid.value;
  userData.value.userType ='patron';
  nickname.value = userData.value.nickname;
  userData.value.editInfoVisi = false;
});

const infoShowFunction = () => {
  userData.value.editInfoVisi = true;
}

const activeName = ref('0');
const headerCarouselRef = ref();
const handleClick = (tab: TabsPaneContext, event: Event) => {
  //console.log(tab, event);
  //console.log(tab.paneName);
  headerCarouselRef.value.setActiveItem(tab.paneName);
  //bookPanelRef.value.setup = true;
  if (tab.paneName == '1') {
    lendRecordRef.value.searchBtnClick();
  }
}

const logoutFun = () => {
  window.localStorage.clear();
  router.push('/');
}


</script>

<style scoped>
.container{
  overflow: hidden;
}

.welcome{
  display: inline-block;
}

.nickname{

  margin-left: 5px;
  margin-right: 10px;
}

.information{
  position: absolute;
  right: 2%;
  top:10px;
}

.header{

  margin-top: 5px;
  margin-left: 1%;
  padding-left: 1%;
}

.panelView{
  width: 100%;

}



</style>