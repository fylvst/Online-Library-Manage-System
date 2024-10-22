<template>
  <div class="loginContainer">
    <div class="block text-center">
      <el-carousel ref="carouselRef" height="400px" indicator-position="none" arrow="never" :autoplay="false">
        <el-carousel-item>
          <div class="loginPart">
            <h1>Login</h1>
            <el-input v-model="userNameInputValue" class="Input" placeholder="Please input your username"/>
            <el-input type="password" v-model="userPasswordInputValue" class="Input"
                      placeholder="Please input your password"/>
            <el-select style="width: 100%" v-model="userTypeSelectValue" class="Input" required="required"
                       placeholder="Please select your user type">
              <el-option
                  v-for="item in userTypeSelectChoices"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
            <br/>
            <el-button class="Input" @click="loginFunction">Login</el-button>

            <el-button class="changeMethod" type="text" @click="changeMethods">Don't have an account?</el-button>
          </div>
        </el-carousel-item>

        <el-carousel-item>
          <div class="regPart">
            <h1>Register</h1>
            <el-input v-model="userNameInputValue" class="Input" placeholder="Please input your username"/>
            <el-input v-model="userPasswordInputValue" type="password" class="Input"
                      placeholder="Please input your password"/>
            <el-input v-model="userPasswordConfirmInputValue" type="password" class="Input"
                      placeholder="Please confirm your password"/>

            <br/>
            <el-button class="Input" @click="registerFunction">register</el-button>

            <el-button class="changeMethod" type="text" @click="changeMethods">Already got an account?</el-button>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>


  </div>

  <div>


  </div>

</template>

<script setup lang="ts">
import {getCurrentInstance, ref} from "vue";
import axios from "axios";
import {ElMessage} from 'element-plus'
import {useRoute, useRouter} from 'vue-router'

const router = useRouter();
const route = useRoute();
const {userTypeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取


const regPartVisiable = ref(false);
const carouselRef = ref();
const userNameInputValue = ref("");
const userPasswordInputValue = ref("");
const userTypeSelectValue = ref("");
const userPasswordConfirmInputValue = ref("");


const resetInput = () => {  //重置输入内容
  userNameInputValue.value = "";
  userPasswordInputValue.value = "";
  userPasswordConfirmInputValue.value = "";
  userTypeSelectValue.value = "";
}

const changeMethods = () => { //切换登录或注册界面
  carouselRef.value.prev();
  resetInput();
}

const loginFunction = () => {
  if (userNameInputValue.value == "" || userPasswordInputValue.value == "" || userTypeSelectValue.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    axios({
      method: "POST",
      url: serverPath + userTypeSelectValue.value + "/login",
      params: {
        username: userNameInputValue.value,
        password: userPasswordInputValue.value,
      }
    }).then(res => {
      let receiveData = res.data;
      if (receiveData.code === 2000) {
        ElMessage({
          showClose: true,
          message: receiveData.message,
          type: 'error',
        });
      } else {
        ElMessage({
          showClose: true,
          message: "Login succeed",
          type: 'success',
        });
        if (userTypeSelectValue.value === 'patron') {
          router.push('patronMainView/bookTable');
          window.localStorage.setItem("pid", receiveData.data.pid);
        } else if (userTypeSelectValue.value === 'staff') {
          router.push('staffMainView/dashBoard');
          window.localStorage.setItem("sid", receiveData.data.sid);
        } else if (userTypeSelectValue.value === 'admin') {
          router.push('adminMainView/dashBoard');
          window.localStorage.setItem("adminUserName", userNameInputValue.value);
        }

        //receiveData.data.pid
      }

    });

  }
}

const registerFunction = () => {
  userTypeSelectValue.value = "patron";
  if (userNameInputValue.value == "" || userPasswordInputValue.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else if (userPasswordConfirmInputValue.value != userPasswordInputValue.value) {
    ElMessage({
      showClose: true,
      message: 'Two password are not same please retry!',
      type: 'warning',
    });
  } else {
    axios({
      method: "POST",
      url: serverPath + userTypeSelectValue.value + "/reg",
      params: {
        username: userNameInputValue.value,
        password: userPasswordInputValue.value,
      }
    }).then(res => {
      let receiveData = res.data;
      if (receiveData.code === 200) {
        ElMessage({
          showClose: true,
          message: "Register succeed",
          type: 'success',
        });
        changeMethods();
      } else {
        ElMessage({
          showClose: true,
          message: receiveData.message,
          type: 'error',
        });
      }
    });

  }


}


</script>

<style scoped>

.loginContainer {
  height: 400px;
  width: 350px;
  padding: 20px;
  box-shadow: 2px 2px 5px #000;
  border-radius: 40px;
  background-color: rgba(255, 255, 255, 0.7);
}

.changeMethod {
  position: absolute;
  bottom: 0;
  right: 0;
}

.Input {
  margin-top: 20px;
}

.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}
</style>