<template>
  <div class="container">
    <div class="tablePart">
      <el-table stripe :height="tableHeight" :data="tableData">
        <el-table-column prop="id" align="center" label="record id" />
        <el-table-column prop="bid" align="center" label="book id" />
        <el-table-column prop="bookname" align="center" label="book name" />
        <el-table-column label="returned" align="center" >
          <template #default="scope">
            <el-tag v-if="scope.row.flag == 1" type="success">returned</el-tag>
            <el-tag v-else type="danger">not returned</el-tag>
          </template>
        </el-table-column>
        <!--      <el-table-column prop="description" label="description" width="1200"/> -->
        <el-table-column label="left days" align="center" width="150">
          <template #default="scope">

            <el-popover v-if="scope.row.flag == 1" effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                <p m="t-0 b-2">borrow time: {{ scope.row.borrowTime }}</p>
                <p m="t-0 b-2">deadline time: {{ scope.row.deadline }}</p>
              </template>
              <template #reference>
                <el-tag type="info">returned</el-tag>
              </template>
            </el-popover>

            <el-popover v-else-if="DateCounter(scope) > 1" effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                <p m="t-0 b-2">borrow time: {{ scope.row.borrowTime }}</p>
                <p m="t-0 b-2">deadline time: {{ scope.row.deadline }}</p>
              </template>
              <template #reference>
                <el-tag type="success">about {{Math.floor(DateCounter(scope))}} days left</el-tag>
              </template>
            </el-popover>

            <el-popover v-else-if="DateCounter(scope) > 0" effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                <p m="t-0 b-2">borrow time: {{ scope.row.borrowTime }}</p>
                <p m="t-0 b-2">deadline time: {{ scope.row.deadline }}</p>
              </template>
              <template #reference>
                <el-tag  type="warning">less than a day</el-tag>
              </template>
            </el-popover>

            <el-popover v-else effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                <p m="t-0 b-2">borrow time: {{ scope.row.borrowTime }}</p>
                <p m="t-0 b-2">deadline time: {{ scope.row.deadline }}</p>
              </template>
              <template #reference>
                <el-tag  type="danger">over due</el-tag>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="fee" align="center" label="fee"/>
        <el-table-column fixed="right" align="center" label="Operations">
          <template #default="scope">
            <el-button v-if="scope.row.flag != 1" type="primary" size="small" @click="returnABook(scope)"
            >return
            </el-button
            >
            <el-tag v-else type="success">returned</el-tag>

          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagePart" style="margin-top: 10px">
      <el-row justify="center">
        <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 30, 40,50,60]"
            :small="false"
            :disabled="false"
            :background="true"
            layout="total, sizes, prev, pager, next, jumper"
            :total="rawData.length"
            @size-change="tableDataRefreash"
            @current-change="tableDataRefreash"
        />
      </el-row>
    </div>


    <el-dialog @closed="returnBookConfirmDialogClosed" v-model="returnBookConfirmDialogVisible" title="Warning"
               width="30%" center>
    <span>
      Have you done paying?
    </span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="returnBookConfirmDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="returnBookConfirmDialogVisible = false">
          Confirm
        </el-button>
      </span>
      </template>
    </el-dialog>



  </div>


</template>


<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
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
  modelValue: any,
  Pid: number,

}>();

const router = useRouter();
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取



console.log(props.modelValue);
const rawData = ref(props.modelValue);
const tableData = ref(rawData.value);
const currentPage = ref(1);
const pageSize = ref(30);
const tableHeight = ref(document.documentElement.clientHeight * 0.75);
const returnBookConfirmDialogVisible = ref(false);
const returnRecordIDTempStorage = ref("");

const tableDataRefreash = () => {
  //rawData.value.reverse();
  let start = pageSize.value * (currentPage.value - 1);
  let end = start + pageSize.value;
  end = end > rawData.value.length ? rawData.value.length : end;
  let tempTable = [];
  while (start < end) {
    tempTable.push(rawData.value[start]);
    start += 1;
  }
  tableData.value = tempTable;
}

tableDataRefreash();

watch(props.modelValue, (newVal, oldVal) => {
  tableDataRefreash();
})

/*const returnABook = (scope: any) => {
  let rowData = scope.row;
  axios({
    method: "POST",
    url: serverPath + "returnBook",
    params: {
      id: rowData.id,
      bid: rowData.bid,
    }
  }).then(res => {
    let receiveData = res.data;
    if (receiveData.code == 200) {
      ElMessage({
        showClose: true,
        message: "Returned succeed",
        type: 'success',
      });
      scope.row.flag = 1;
      tableDataRefreash();
    } else {
      ElMessage({
        showClose: true,
        message: receiveData.message,
        type: 'error',
      });
    }
  });
}*/

const returnABook = (scope: any) => {
  let rowData = scope.row;
  window.open("http://101.201.143.17:8082/alipay/pay?&subject=returnBook&traceNo=" + rowData.id + "&totalAmount=" + rowData.fee);
  returnRecordIDTempStorage.value = rowData.id;
  returnBookConfirmDialogVisible.value = true;
}

const DateCounter = (scope: any) => {
  let nowDate = new Date();
  let deadLineDate = new Date(scope.row.deadline);
  let leftTime = deadLineDate - nowDate;
  leftTime /= 1000 * 24 * 3600;
  return leftTime;
}

const returnBookConfirmDialogClosed = () => {
  const userID = props.Pid;
  axios({
    method: "POST",
    url: serverPath + "getRecord",
    params: {
      pid: userID,
    }
  }).then(res => {
    let receiveData = res.data.data;
    for (let i = 0; i < receiveData.length; i++) {
      if (receiveData[i].id == returnRecordIDTempStorage.value) {
        for (let j = 0; j < rawData.value.length; j++) {
          if (rawData.value[j].id == returnRecordIDTempStorage.value) {
            if(!receiveData[i].flag){
              ElMessage({
                showClose: true,
                message: "You have not pay yet!",
                type: 'error',
              });
            }
            else{
              ElMessage({
                showClose: true,
                message: "success!",
                type: 'success',
              });
            }
            rawData.value[j].flag = receiveData[i].flag;
            break;
          }
        }
        break;
      }
    }
    tableDataRefreash();
  });

}


</script>

<style scoped>

.container {
  width: 100%;
  max-width: 1490px;
}

.tablePart {

}

.pagePart {
  margin: 0 auto;
}
</style>