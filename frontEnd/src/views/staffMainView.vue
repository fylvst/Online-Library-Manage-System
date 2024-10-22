<template>
  <div class="container">
    <el-header class="header">

      <el-tabs style="max-width: 99%" v-model="activeName" class="tabs" @tab-click="handleClick">
        <el-tab-pane label="Book Management" name="0"></el-tab-pane>
        <el-tab-pane label="Lend Record Management" name="1"></el-tab-pane>
        <el-tab-pane label="NotReadyYet" name="2"></el-tab-pane>
        <el-tab-pane label="NotReadyYet" name="3"></el-tab-pane>
      </el-tabs>

      <div class="information">
        <p class="welcome">Welcome,</p>
        <el-button class="nickname" type="text" @click="infoShowFunction"> {{ userData.nickname }}</el-button>
        <el-button class="nickname" type="danger" link @click="logoutFun">logout</el-button>
      </div>
    </el-header>

    <div class="panelView">
      <el-carousel ref="headerCarouselRef" class="carousel" height="700px" :autoplay="false" indicator-position="none"
                   arrow="never">
        <el-carousel-item height="1000px" :key="1">
          <book-view-pannel v-model="bookPanelRef"></book-view-pannel>
        </el-carousel-item>
        <el-carousel-item height="1000px" :key="2">
          <staff-lend-record-page ref="lendRecordRef"></staff-lend-record-page>
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
import BookViewPannel from "@/components/staff_bookView_pannel.vue";
import StaffLendRecordPage from "@/components/staff_lendRecord_panenel.vue";

const router = useRouter();
const route = useRoute();
//const sid = ref(route.params.sid);
const sid = ref(window.localStorage.getItem("sid"));

const nickname = ref("placeholder");
const userData = ref();
const bookPanelRef = ref({
  setup: false,
});
const lendRecordRef = ref();

const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取


axios({
  method: "POST",
  url: serverPath + "staff/getBySid",
  params: {
    sid: sid.value
  }
}).then(res => {
  userData.value = res.data.data;
  userData.value.sid = sid.value;
  userData.value.userType = 'staff';
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
  //bookPanelRef.value.QueryAllBook();

  bookPanelRef.value.setup = true;

  if (tab.paneName == '1') {
    lendRecordRef.value.clearRecord();
  }
}

const logoutFun = () => {
  window.localStorage.clear();
  router.push('/');
}

</script>

<style scoped>
.container {
  overflow: hidden;
}

.welcome {
  display: inline-block;
}

.nickname {

  margin-left: 5px;
  margin-right: 10px;
}

.information {
  position: absolute;
  right: 2%;
  top: 10px;
}

.header {

  margin-top: 5px;
  margin-left: 1%;
  padding-left: 1%;
}

.panelView {
  width: 100%;

}


</style>