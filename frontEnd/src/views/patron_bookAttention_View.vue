<template>
  <div class="searchPanel">


    <el-row align="middle">
      <el-col>
        <patron_attation-list_table v-model="tableData"></patron_attation-list_table>
      </el-col>
    </el-row>


  </div>


</template>

<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import {Search} from '@element-plus/icons-vue'
import BookDataTableWithPagination from "@/components/staff_bookData_table.vue";
import BookAddingWindow from "@/components/staff_bookAddingWindow.vue";
import Patron_bookData_table from "@/components/patron_bookData_table.vue";
import {ElMessage} from "element-plus";
import Patron_attationList_table from "@/components/patron_attationList_table.vue";
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
const {
  serverPath
} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const bookCategorySelectChoices = ref([]);
const getBookSelectChoice = () => {
  axios({
    method: "GET",
    url: serverPath + "category/list"
  }).then(res => {
    let receiveData = res.data.data;
    receiveData.sort(function (a: any, b: any) {
      return a.cateName < b.cateName ? -1 : 1;
    });
    for (let i = 0; i < receiveData.length; i++) {
      bookCategorySelectChoices.value.push({
        lable: receiveData[i].cid,
        value: receiveData[i].cateName
      })
    }
  });
}
getBookSelectChoice();

let testBookData = [
  {
    "isDelete": null,
    "createTime": null,
    "updateTime": null,
    "bid": 768,
    "name": "Thus Spoke Zarathustra",
    "author": "Friedrich Nietzsche",
    "cid": 1,
    "cateName": "Philosophy",
    "nums": 1,
    "available": 1,
    "description": "Nietzsche was one of the most revolutionary and subversive thinkers in Western philosophy, and Thus Spoke Zarathustra remains his most famous and influential work. It describes how the ancient Persian prophet Zarathustra descends from his solitude in the mountains to tell the world that God is dead and that the Superman, the human embodiment of divinity, is his successor. With blazing intensity and poetic brilliance, Nietzsche argues that the meaning of existence is not to be found in religiouspieties or meek submission, but in an all-powerful life force: passionate, chaotic & free.",
    "isbn": "9780141904320",
    "publisher": "Penguin UK"
  },
];
const rawData = ref();
const tableData = ref(testBookData);
const cataTypeSelectValue = ref();
const inputValue = ref("");


const QueryAllBook = async () => {
  tableData.value.length = 0;
  axios({
    method: "GET",
    url: serverPath + "attention/list",
    params: {
      pid: window.localStorage.getItem("pid"),
    }
  }).then(res => {
    let tempResult = res.data;
    if (tempResult.code == 200) {
      for (let i = 0; i < tempResult.data.length; i++) {
        tableData.value.push(tempResult.data[i]);
      }
      rawData.value = tableData.value;
    }

  });
}

// eslint-disable-next-line no-undef
defineExpose({
  QueryAllBook,
})

QueryAllBook();

</script>

<style scoped>
.table-col {
  max-width: 1680px;
}
</style>