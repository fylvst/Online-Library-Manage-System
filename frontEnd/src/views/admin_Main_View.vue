<template>
  <div class="container">
    <el-header class="header">

      <el-tabs style="max-width: 99%" v-model="activeName" class="tabs" @tab-click="handleClick">
        <el-tab-pane label="Dash Board" name="dashBoard"></el-tab-pane>
        <el-tab-pane label="Patron Management" name="patronManagement"></el-tab-pane>
        <el-tab-pane label="Staff Management" name="staffManagement"></el-tab-pane>
      </el-tabs>

      <div class="information">
        <p class="welcome">Welcome,{{ nickName }}</p>

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
const nickName = ref(window.localStorage.getItem("adminUserName"));

const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取

const InitHeader = () => {
  let temp: any;
  temp = window.location.href.split('/');
  temp = temp[temp.length - 1];
  return temp;
}

const activeName = ref(InitHeader());

const handleClick = (tab: TabsPaneContext, event: Event) => {
  if (tab.paneName == 'patronManagement') {
    router.push('/adminMainView/patronManagement');
  }
  if (tab.paneName == 'staffManagement') {
    router.push('/adminMainView/staffManagement');
  }
  if (tab.paneName == 'dashBoard') {
    router.push('/adminMainView/dashboard');
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