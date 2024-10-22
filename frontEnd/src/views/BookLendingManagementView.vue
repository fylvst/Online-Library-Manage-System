<template>


  <!-- book information prohibit START -->
  <el-table :data="tableData" stripe border>
    <el-table-column prop="ID" label="ID" sortable/>
    <el-table-column prop="name" label="Name" sortable/>
    <el-table-column prop="type" label="Type" sortable/>
    <el-table-column prop="price" label="Price" sortable/>
    <el-table-column prop="overdue_payment" label="Overdue payment" sortable/>
    <el-table-column prop="book_category" label="Category" sortable/>
    <el-table-column prop="state" label="State">
      <template #default="scope">
        <el-tag class="ml-2" v-if="scope.row.state">In store</el-tag>
        <el-tag class="ml-2" type="warning" v-else>Lending</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="Operations">
      <template #default="scope">
        <el-button
            link
            type="primary"
            size="small"
            @click.prevent="detailButtonJumper(scope)"
        >
          Detail
        </el-button>

      </template>
    </el-table-column>
  </el-table>
  <!-- book information prohibit END -->

  <!-- book adding window setup START -->
  <br/>
  <el-button type="primary" @click="centerDialogVisible = true">
    add a new record
  </el-button>

  <el-dialog
      v-model="centerDialogVisible"
      title="input the new record"
      width="40%"
      destroy-on-close
      center
      @close="newRecordWindowCloseFun"
  >

    <div>
      <el-input v-model="newBookIDInput" @change="bookIDInputOnChangeFunction" class="m-2 newRecord" required="required"
                placeholder="Please input the book ID"/>

      <el-input v-model="newTypeSelectValue" class="newRecord" required="required"
                disabled placeholder="Record type decide by book ID"/>

      <el-input-number v-model="lendHoursInputValue" v-if="lendHoursInputVisible" class="m-2 newRecord"
                       required="required"
                       placeholder="Please input the book's lend hours"/>

      <el-input v-model="userIDInputValue" class="m-2 newRecord" required="required"
                placeholder="Please input the user ID"/>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="newRecordWindowCloseFun">Cancel</el-button>
        <el-button type="primary" @click="addingANewRecord">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
  <!-- book adding window setup END -->

</template>

<script setup lang="ts">
import {getCurrentInstance, ref} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import {Table} from "element-plus";

const router = useRouter();

const {serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const centerDialogVisible = ref(false);
let StateList: any//store the original data
const tableData = ref([//store the data showed in the browser table
  {
    ID: 'test',
    name: 'test',
    type: 'test',
    price: 5000,
    overdue_payment: 200,
    book_category: "aaa",
    book_snapshot: "it's a book",
    state: true,
  }, {
    ID: 'test',
    name: 'test',
    type: 'test',
    price: 5000,
    overdue_payment: 200,
    book_category: "aaa",
    book_snapshot: "it's a book",
    state: false,
  }
])


axios({
  method: "POST",
  url: serverPath + "bookData/queryAllBookData"
}).then(res => {    //异步进行函数
  tableData.value = eval(res.data);
  axios({
    method: "POST",
    url: serverPath + "lendRecord/queryBookRecordAmount"
  }).then(res => {
    StateList = eval(res.data);
    for (let i = 0; i < tableData.value.length; i++) {
      for (let j = 0; i < StateList.length; j++) {
        if (StateList[j]["book_ID"] == tableData.value[i]["ID"]) {
          tableData.value[i]["state"] = (StateList[j]["recordAmount"] % 2 == 0);
          break;
        }
      }
    }
  });
});

const newBookIDInput = ref();
const newTypeSelectValue = ref();
const lendHoursInputVisible = ref(false);
const lendHoursInputValue = ref();
const userIDInputValue = ref();

const newRecordInputReset = () => {
  newBookIDInput.value = "";
  newTypeSelectValue.value = "";
  lendHoursInputVisible.value = false;
  userIDInputValue.value = "";
}

const newRecordWindowCloseFun = () => {
  centerDialogVisible.value = false;
  newRecordInputReset();
}

const addingANewRecord = () => {
  if (newBookIDInput.value == "" || lendHoursInputValue.value == 0 || userIDInputValue.value == "") {
    alert("Input incomplete!!!");
    return;
  }
  axios({
    method: "POST",
    url: serverPath + "lendRecord/addANewRecord",
    params: {
      book_ID: newBookIDInput.value,
      lender_ID: userIDInputValue.value,
      recordType: newTypeSelectValue.value,
      lendingHours: lendHoursInputValue.value,
    }
  }).then(res => {    //异步进行函数
    for (let i = 0; i < tableData.value.length; i++)
      if (tableData.value[i].ID == newBookIDInput.value) {
        tableData.value[i].state = (!tableData.value[i].state);
        break;
      }
    for (let i = 0; i < StateList.length; i++)
      if (StateList[i]["book_ID"] == newBookIDInput.value) {
        StateList[i]['recordAmount'] += 1;
        break;
      }
    newRecordWindowCloseFun();
  });
}

const bookIDInputOnChangeFunction = () => {
  let i = 0;
  for (i = 0; i < StateList.length; i++)
    if (StateList[i]['book_ID'] == newBookIDInput.value)
      break;
  if (StateList.length == i)
    alert("Wrong book ID input!!!");
  else {
    newTypeSelectValue.value = StateList[i]['recordAmount'] % 2 == 0 ? 'lend' : 'return';
    lendHoursInputVisible.value = !(StateList[i]['recordAmount'] % 2);
    lendHoursInputValue.value = StateList[i]['recordAmount'] % 2 == 0 ? 0 : -1;
  }
}


const detailButtonJumper = (scope: any) => {
  let Data = scope.row;
  router.push({
    name: 'lendRecordDetail',
    query: {
      id: Data.ID,
    }
  })
}


</script>

<style scoped>
.newRecord {
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}
</style>