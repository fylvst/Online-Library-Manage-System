<template>
 <div> <el-button type="success" @click="cateAdding.editInfoVisi=true">Adding A New Category</el-button>
   <staff_add-cate_popout-windows v-model="cateAdding"></staff_add-cate_popout-windows>

   <staff_cate_table style="margin: 0 auto;" v-model="tableData"></staff_cate_table></div>
</template>

<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import Staff_cate_table from "@/components/staff_cate_table.vue";
import Staff_addCate_popoutWindows from "@/components/staff_addCate_popoutWindows.vue";
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
const testData = [{
  isDelete: null,
  createTime: null,
  updateTime: null,
  cid: 1,
  cateName: "Philosophy",
},]
const {serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const tableData = ref(testData);

const getCateData = () => {
  axios({
    method: "GET",
    url: serverPath + "category/list"
  }).then(res => {
    let receiveData = res.data.data;
    tableData.value.length = 0;
    for (let i = 0; i < receiveData.length; i++) {
      tableData.value.push(receiveData[i]);
    }
  });
}

const cateAdding = ref({
  editInfoVisi: false,
  newData: false,
})

getCateData();

watch(
    cateAdding.value,
    (newval, oldval) => {
      if (cateAdding.value.newData) {
        getCateData();
        cateAdding.value.newData = false;
      }
    },
);

</script>

<style scoped>

</style>