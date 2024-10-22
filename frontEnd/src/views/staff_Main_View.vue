<template>
  <div class="container">
    <el-header class="header">

      <el-tabs style="max-width: 99%" v-model="activeName" class="tabs" @tab-click="handleClick">
        <el-tab-pane label="Dash Board" name="dashBoard"></el-tab-pane>
        <el-tab-pane label="Book Management" name="bookTable"></el-tab-pane>
        <el-tab-pane label="Lend Record Management" name="bookLending"></el-tab-pane>
        <el-tab-pane label="Category Management" name="cateManagement"></el-tab-pane>
      </el-tabs>

      <div class="information">
        <p class="welcome">Welcome,</p>
        <el-button class="nickname" type="text" @click="infoShowFunction"> {{ userData.nickname }}</el-button>
        <el-button class="nickname" type="danger" link @click="logoutFun">logout</el-button>
      </div>
    </el-header>

    <div class="view_panel">
      <router-view v-slot="{ Component }">
        <transition name="fade-right" mode="out-in">
          <component :is="Component"/>
        </transition>
      </router-view>
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

const router = useRouter();
const route = useRoute();
const sid = ref(window.localStorage.getItem("sid"));

const nickname = ref("placeholder");
const userData = ref();

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

const InitHeader = () => {
  let temp:any;
  temp = window.location.href.split('/');
  temp = temp[temp.length - 1];
  return temp;
}

const activeName = ref(InitHeader());

const handleClick = (tab: TabsPaneContext, event: Event) => {
  if (tab.paneName == 'bookTable') {
    router.push('/staffMainView/bookTable');
  }
  if (tab.paneName == 'bookLending') {
    router.push('/staffMainView/bookLending');
  }
  if (tab.paneName == 'cateManagement') {
    router.push('/staffMainView/cateManagement');
  }
  if (tab.paneName == 'dashBoard') {
    router.push('/staffMainView/dashboard');
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

.fade-right-enter-to,
.fade-right-leave-from {
  opacity: 1;
  transform: none;
}

.fade-right-enter-active,
.fade-right-leave-active {
  transition: all 0.5s;
}


.fade-right-enter-from,
.fade-right-leave-to {
  opacity: 0;
  transform: translateX(20px);
}



</style>