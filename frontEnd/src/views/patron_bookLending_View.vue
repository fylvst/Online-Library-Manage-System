<template>

  <div style="margin: 0 auto;">

        <patron_lend-record_table style="margin: 0 auto;" v-model="tableData" v-model:Pid="Pid"></patron_lend-record_table>


    </div>

</template>

<script lang='ts' setup>
import {getCurrentInstance, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";
import axios from "axios";
import LendRecordTable from "@/components/staff_lendRecord_table.vue";
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


*/
import {Search} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import Patron_lendRecord_table from "@/components/patron_lendRecord_table.vue";

const router = useRouter();
const { serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const typeSelectChoices = ref([]);
const getTypeSelectChoice = () => {
  axios({
    method: "GET",
    url: serverPath + "category/list"
  }).then(res => {
    let receiveData = res.data.data;
    receiveData.sort(function (a: any, b: any) {
      return a.cateName < b.cateName ? -1 : 1;
    });
    for (let i = 0; i < receiveData.length; i++) {
      typeSelectChoices.value.push({
        value: receiveData[i].cid,
        lable: receiveData[i].cateName
      })
    }
  });
}
getTypeSelectChoice();

const tableData = ref([]);

const route = useRoute();
//const Pid = ref(route.params.pid);
const Pid = ref(window.localStorage.getItem("pid"));


const searchBtnClick = () => {
  if (Pid.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    axios({
      method: "POST",
      url: serverPath + "getRecord",
      params: {
        pid: Pid.value,
      }
    }).then(res => {
      let receiveData = res.data.data;
      tableData.value.length = 0;
      for (let i = 0; i < receiveData.length; i++) {
        tableData.value.push(receiveData[i]);
      }
    });
  }
}
searchBtnClick();

// eslint-disable-next-line no-undef
defineExpose({
  searchBtnClick,
})
</script>

<style scoped>

</style>