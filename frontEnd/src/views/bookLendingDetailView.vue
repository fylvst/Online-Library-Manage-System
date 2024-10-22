<template>
  <h1>Book {{ ID }} Lending Record Detail Page</h1>

  <div v-if="tableData.length%2!=0">
    <h1>This Book Need To Be Returned before <span class="important"> {{ Returned_Date }}</span></h1>
    <div v-if="delayHours > 0">
      <h1> This book is late for <span class="important">  {{ delayHours }}</span> hour(s)</h1>
      <h1> And need to be fined by <span class="important">  {{delayHours * bookData[0]["overdue_payment"]}}</span> cent(s)</h1>
    </div>


  </div>


  <!-- book information prohibit START -->
  <el-table :data="tableData" stripe border>
    <el-table-column prop="record_ID" label="record_ID" sortable/>
    <el-table-column prop="happenedTime" label="happenedTime" sortable/>
    <el-table-column prop="lender_ID" label="lender_ID" sortable/>
    <el-table-column prop="lendingHours" label="lendingHours" sortable/>
    <el-table-column prop="recordType" label="recordType" sortable/>
  </el-table>
  <!-- book information prohibit END -->


</template>

<script setup lang="ts">
import {useRoute, useRouter} from 'vue-router'
import {getCurrentInstance, ref} from "vue";
import axios from "axios";
import {Table} from "element-plus";

const router = useRouter();
const route = useRoute();
const ID = ref(route.query.id);
const Returned_Date = ref();
let nowTime = new Date();
let delayHours = ref();
const {serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取

const tableData = ref([//store the data showed in the browser table
  {
    record_ID: 1,
    happenedTime: '1',
    recordType: '1',
    lender_ID: 5000,
    lendingHours: 200,
  }, {
    record_ID: 1,
    happenedTime: '1',
    recordType: '1',
    lender_ID: 5000,
    lendingHours: 200,
  }
])

let bookData: any;

axios({
  method: "POST",
  url: serverPath + "lendRecord/queryRecordByBookID",
  params: {
    book_ID: ID.value,
  }
}).then(res => {
  tableData.value = eval(res.data);
  axios({
    method: "POST",
    url: serverPath + "bookData/queryBookByID",
    params: {
      id: ID.value,
    }
  }).then(res => {
    bookData = eval('[' + res.data + ']');
    axios({
      method: "POST",
      url: serverPath + "lendRecord/queryReturnTimeByRecordID",
      params: {
        RecordID: tableData.value[tableData.value.length - 1].record_ID,
      }
    }).then(res => {
      let tempData = eval('[' + res.data + ']');
      Returned_Date.value = tempData[0]['returnTime'];
      delayHours.value = ((Date.parse(nowTime) - Date.parse(Returned_Date.value)) / 1000 / 60 / 60).toFixed(0);
      console.log(delayHours.value);
    });
  });


});

</script>

<style scoped>
.important{
  color: red;
}
</style>