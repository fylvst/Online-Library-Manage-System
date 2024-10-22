<template>

  <div class="container">
    <el-dialog width="600px" @close="dialogCloseEvent" :model-value="props.modelValue.editInfoVisi"
               title="New Category Input">

      <el-form
          :label-position="'left'"
          label-width="120px"
          style="max-width: 600px"
      >

        <el-form-item label="category name:">
          <el-input v-model="bookNameInput"/>
        </el-form-item>


        <el-button @click="resetInputValue">reset</el-button>
        <el-button @click="uploadNewBook">submit</el-button>

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
  serverPath,
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

const bookNameInput = ref();


const resetInputValue = () => {
  bookNameInput.value = "";
}

const dialogCloseEvent = () => {
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.editInfoVisi = false;
  resetInputValue();
}


const uploadNewBook = () => {
  if (bookNameInput.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    axios({
      method: "POST",
      url: serverPath + "category/add",
      params: {
        cateName: bookNameInput.value,
      },
    }).then(res => {
      if (res.data.code == 200) {
        ElMessage({
          showClose: true,
          message: "adding category succeed",
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