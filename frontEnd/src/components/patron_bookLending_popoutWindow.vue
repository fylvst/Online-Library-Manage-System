<template>
  <div class="container">
    <el-dialog width="450px" @close="dialogCloseEvent" :model-value="props.modelValue.editInfoVisi" title="Lend a book">
      <el-form
          :label-position="'left'"
          label-width="80px"
          style="max-width: 450px"
      >

        <el-form-item label="book ID:">
          <el-input :placeholder="props.modelValue.data.bid" disabled/>
        </el-form-item>


        <el-form-item label="patron ID:">
          <el-input v-model="pidInputValue" disabled placeholder="Please input the patron ID"/>
        </el-form-item>


        <el-form-item label="lend days:">
          <el-input-number v-model="lendDaysInputValue" style="width: 100%"
                        disabled   placeholder="Input the days you wish to lend"/>
        </el-form-item>



        <el-button @click="editUserInfo">submit</el-button>

      </el-form>

    </el-dialog>
  </div>

</template>

<script lang='ts' setup>
import {getCurrentInstance, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const router = useRouter();
const route = useRoute();
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取

// eslint-disable-next-line no-undef
const props = defineProps<{
  modelValue: any;
}>();

const pidInputValue = ref(window.localStorage.getItem("pid"));
const lendDaysInputValue = ref("30");

const resetInputValue = () => {
  lendDaysInputValue.value = "30";
}

const editUserInfo = () => {
  let transData = {
    pid: pidInputValue.value,
    bid: props.modelValue.data.bid,
    days: lendDaysInputValue.value,
  }
  if (pidInputValue.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    axios({
      method: "POST",
      url: serverPath + "borrowBook",
      params: transData,
    }).then(res => {
      let receiveData = res.data;
      if (receiveData.code === 200) {
        ElMessage({
          showClose: true,
          message: "Succeed Lend",
          type: 'success',
        });
        // eslint-disable-next-line vue/no-mutating-props
        props.modelValue.editInfoVisi = false;
        // eslint-disable-next-line vue/no-mutating-props
        props.modelValue.succeed = true;
        resetInputValue();
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