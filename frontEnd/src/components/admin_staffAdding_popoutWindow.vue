<template>

  <div class="container">
    <el-dialog width="600px" @close="dialogCloseEvent" :model-value="props.modelValue.editInfoVisi"
               title="New Staff Adding">

      <el-form
          :label-position="'left'"
          label-width="120px"
          style="max-width: 600px"
      >

        <el-form-item label="user name:">
          <el-input v-model="userNameInput"/>
        </el-form-item>

        <el-form-item label="password:">
          <el-input v-model="passwordInput"/>
        </el-form-item>


        <el-button @click="resetInputValue">reset</el-button>
        <el-button @click="uploadNewUser">submit</el-button>

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
const {
  serverPath
} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取

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

const userNameInput = ref("");
const passwordInput = ref("");


const resetInputValue = () => {
  userNameInput.value = "";
  passwordInput.value = "";
}

const dialogCloseEvent = () => {
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.editInfoVisi = false;
  resetInputValue();
}


const uploadNewUser = () => {
  if (userNameInput.value == "" || passwordInput.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    let transData = {
      username: userNameInput.value,
      password: passwordInput.value,
    };
    axios({
      method: "POST",
      url: serverPath + "staff/reg",
      params: transData,
    }).then(res => {
      if (res.data.code == 200) {
        ElMessage({
          showClose: true,
          message: "adding staff succeed",
          type: 'success',
        });
        // eslint-disable-next-line vue/no-mutating-props
        props.modelValue.newData = true;
      } else {
        ElMessage({
          showClose: true,
          message: res.data.message,
          type: 'error',
        });
      }
      dialogCloseEvent();
    });
  }


}


</script>

<style scoped>


</style>