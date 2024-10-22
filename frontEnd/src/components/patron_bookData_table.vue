<template>

  <div class="tablePart">
    <el-table
        stripe
        :border="true"
        :data="tableData"
        :height="tableHeight"
    >
      <el-table-column type="expand">
        <template #default="props">


          <div style="margin:0  5%">
            <p m="t-0 b-2">book amount: {{ props.row.nums }}</p>
            <p m="t-0 b-2">category: {{ props.row.cateName }}</p>
            <p m="t-0 b-2">author: {{ props.row.author }}</p>
            <p m="t-0 b-2">isbn: {{ props.row.isbn }}</p>
            <p m="t-0 b-2">publisher: {{ props.row.publisher }}</p>
            <p m="t-0 b-2">description</p>
            <el-popover effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                <div>{{ props.row.description }}</div>
              </template>
              <template #reference>
                <el-tag type="info">hover to see description</el-tag>
              </template>
            </el-popover>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" type="index"/>
      <el-table-column prop="bid" align="center" label="id"/>
      <el-table-column prop="name" align="center" label="book name"/>
      <el-table-column label="available" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.available > 1" type="success">{{ scope.row.available }} books available</el-tag>
          <el-tag v-else-if="scope.row.available!= 0" type="warning">only 1 book left</el-tag>
          <el-tag v-else type="danger">not available</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="author" align="center" label="author"/>
      <el-table-column prop="location" align="center" label="location"/>


      <!--      <el-table-column prop="description" label="description" width="1200"/> -->
      <el-table-column align="center" label="Operations">
        <template #default="scope">
          <el-button v-if="scope.row.available>0" link type="primary" size="small" @click="lendABook(scope)">Lend
          </el-button>
          <el-button v-else link type="warning" size="small" @click="addAttention(scope)">Add to attention
          </el-button>
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


  <book-lending_popout-window :model-value="bookLendingData"></book-lending_popout-window>

</template>


<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import BookLending_popoutWindow from "@/components/patron_bookLending_popoutWindow.vue";
import axios from "axios";
import {ElMessage} from "element-plus";

// eslint-disable-next-line no-undef
const props = defineProps<{
  modelValue: any;
}>();

const router = useRouter();
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const bookLendingData = ref({
  editInfoVisi: false,
  succeed: false,
  data: {
    bid: 1,
  },
});
const tempScope = ref();

console.log(props.modelValue);
const rawData = ref(props.modelValue);
const tableData = ref(rawData.value);
const currentPage = ref(1);
const pageSize = ref(30);

const tableHeight = ref(document.documentElement.clientHeight * 0.73);

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

const lendABook = (scope: any) => {
  let data = scope.row;
  tempScope.value = scope;
  bookLendingData.value.editInfoVisi = true;
  bookLendingData.value.data = scope.row;
}

watch(bookLendingData.value, async (newVal, oldVal) => {
  if (bookLendingData.value.succeed == true) {
    await console.log(bookLendingData.value.data.bid);
    for (let i = 0; i < rawData.value.length; i++) {
      if (rawData.value[i].bid == bookLendingData.value.data.bid) {
        rawData.value[i].available -= 1;
        break;
      }
    }
    bookLendingData.value.succeed = false;
  }
})

const addAttention = (scope: any) => {
  const data = scope.row;
  axios({
    method: "POST",
    url: serverPath + "attention/add",
    params: {
      pid: window.localStorage.getItem("pid"),
      bid: data.bid,
    },
  }).then(res => {
    let receiveData = res.data;
    if (receiveData.code == 200) {
      ElMessage({
        showClose: true,
        message: "Succeed Add",
        type: 'success',
      });
    } else {
      ElMessage({
        showClose: true,
        message: receiveData.message,
        type: 'warning',
      });
    }

  })
}


</script>

<style scoped>


</style>