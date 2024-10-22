<template>
  <div class="searchPanel">
    <el-row align="middle">
      <el-col>

        <el-input
            v-model="Pid"
            placeholder="Please input the patron id"
            class="input-with-select"
            style="width: 30%"
        >
          <template #append>
            <el-button @click="searchBtnClick" :icon="Search"/>
          </template>
        </el-input>
        
      </el-col>
    </el-row>


  </div>


  <div >
    <lend-record-table style="margin: 0 auto;" v-model="tableData" v-model:Pid="Pid"></lend-record-table>
  </div>

</template>

<script lang='ts' setup>
import {getCurrentInstance, ref} from 'vue'
import {useRouter} from "vue-router";
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
const router = useRouter();
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const Pid = ref("");

const tableData = ref([]);

const searchBtnClick = () => {
  if(Pid.value == ""){
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  }
  else{
    axios({
      method: "POST",
      url: serverPath + "getRecord",
      params: {
        pid:Pid.value,
      }
    }).then(res => {
      let receiveData = res.data.data;
      console.log(receiveData);
      tableData.value.length = 0;
      for(let i=0;i<receiveData.length;i++){
        tableData.value.push(receiveData[i]);
      }
    });
  }
}

const clearRecord = () => {
  Pid.value = "";
  tableData.value.length = 0;
}

// eslint-disable-next-line no-undef
defineExpose({
  clearRecord,
})

</script>

<style scoped>

</style>