<template>
  <div class="container">
    <el-dialog width="450px" @close="dialogCloseEvent" :model-value="props.modelValue.editInfoVisi" title="Lend a book">
      <el-form
          :label-position="'left'"
          label-width="150px"
          style="max-width: 450px"
      >

        <el-form-item label="category ID:">
          <el-input :placeholder="props.modelValue.data.cid" disabled/>
        </el-form-item>


        <el-form-item label="category name:">
          <el-input v-model="cateNameInputValue" placeholder="Please input the category name"/>
        </el-form-item>

        <el-button @click="resetInputValue">reset</el-button>
        <el-button @click="updateCate">submit</el-button>

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

// eslint-disable-next-line no-undef
const props = defineProps<{
  modelValue: any;
}>();

const cateNameInputValue = ref("");

const resetInputValue = () => {
  cateNameInputValue.value = "";
}

const updateCate = () => {
  let transData = {
    cid: props.modelValue.data.cid,
    cateName: cateNameInputValue.value,
  }
  if (cateNameInputValue.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    axios({
      method: "PUT",
      url: serverPath + "category/update",
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
        // eslint-disable-next-line vue/no-mutating-props
        props.modelValue.data.newName = cateNameInputValue.value;
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