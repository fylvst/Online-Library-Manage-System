<template>
  <div class="container">
    <el-dialog width="450px" @close="dialogCloseEvent" :model-value="props.modelValue.editInfoVisi"
               title="User information edit">
      <el-form
          :label-position="'left'"
          label-width="80px"
          style="max-width: 400px"
      >

        <el-form-item label="username:">
          <el-input v-model="usernameInputValue" :placeholder="props.modelValue.username" disabled/>
        </el-form-item>


        <el-form-item label="nickname:">
          <el-input v-model="nicknameInputValue" :placeholder="props.modelValue.nickname"/>
        </el-form-item>


        <el-form-item v-if="props.modelValue.userType === 'patron'" label="age:">
          <el-input v-model="ageInputValue" :placeholder="props.modelValue.age"/>
        </el-form-item>


        <el-form-item label="phone:">
          <el-input v-model="phoneInputValue" :placeholder="props.modelValue.phone"/>
        </el-form-item>

        <el-form-item label="email:">
          <el-input v-model="emailInputValue" :placeholder="props.modelValue.email"/>
        </el-form-item>

        <el-button @click="resetInputValue">reset</el-button>
        <el-button @click="editUserInfo">submit</el-button>

      </el-form>

    </el-dialog>
  </div>

</template>

<script lang='ts' setup>
import {getCurrentInstance, ref} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const router = useRouter();
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
/*
axios({
    method: "POST",
    url: serverPath + "bookData/deleteByID",
    params: {
      id: eval(scope.row.ID),
    }
  });
  
 router.push({
    name: 'bookDetail',
    query: {
      id: Data.ID,
    }
  })
  
  const router = useRouter();
  const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
*/
// eslint-disable-next-line no-undef
const props = defineProps<{
  modelValue: any;
}>();

const testFun = () => {
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.nickname = "aaaaaa";
}

const usernameInputValue = ref("");
const nicknameInputValue = ref("");
const ageInputValue = ref("");
const phoneInputValue = ref("");
const emailInputValue = ref("");
const resetInputValue = () => {
  usernameInputValue.value = "";
  nicknameInputValue.value = "";
  ageInputValue.value = "";
  phoneInputValue.value = "";
  emailInputValue.value = "";
}

const editUserInfo = () => {
  let userType = props.modelValue.userType;
  let transData = {
    nickname: nicknameInputValue.value === "" ? props.modelValue.nickname : nicknameInputValue.value,
  };
  if (!(props.modelValue.phone === null && phoneInputValue.value === "")) {
    transData.phone = phoneInputValue.value === "" ? props.modelValue.phone : phoneInputValue.value;
  }
  if (!(props.modelValue.email === null && emailInputValue.value === "")) {
    transData.email = emailInputValue.value === "" ? props.modelValue.email : emailInputValue.value;
  }
  //用户类别区分进行数据插入
  if (userType === "patron") {
    if (!(props.modelValue.age === null && ageInputValue.value === "")) {
      transData.age = ageInputValue.value === "" ? props.modelValue.age : ageInputValue.value;
    }
    transData.pid = props.modelValue.pid;
  } else if (userType === "staff") {
    transData.sid = props.modelValue.sid;
  }

  console.log(transData);
  axios({
    method: "POST",
    url: serverPath + props.modelValue.userType + "/updateInfo",
    params: transData,
  }).then(res => {
    let receiveData = res.data;
    if (receiveData.code === 200) {
      ElMessage({
        showClose: true,
        message: "Edit succeed",
        type: 'success',
      });
      console.log(props.modelValue);
      // eslint-disable-next-line vue/no-mutating-props
      props.modelValue.succeedAdding = true;
      console.log(props.modelValue);
      // eslint-disable-next-line vue/no-mutating-props
      props.modelValue.editInfoVisi = false;

    } else {
      ElMessage({
        showClose: true,
        message: receiveData.message,
        type: 'error',
      });
    }
  })
  ;

}

const dialogCloseEvent = () => {
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.editInfoVisi = false;
}

</script>

<style scoped>
.container {
  width: 400px;
  height: 400px;
}

</style>