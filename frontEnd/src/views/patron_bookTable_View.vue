<template>
  <div class="searchPanel">
    <el-row align="middle">
      <el-col>

        <el-input
            v-model="inputValue"
            placeholder="Please input"
            class="input-with-select"
            style="width: 30%"
        >
          <template #prepend>
            <el-select @change="cataQueryFunction" v-model="cataTypeSelectValue" placeholder="Select"
                       style="width: 115px">
              <el-option
                  v-for="item in bookCategorySelectChoices"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </template>
          <template #append>
            <el-button @click="searchBtnClick" :icon="Search"/>
          </template>
        </el-input>
        <el-button type="info" style="margin-left: 2%" @click="reset2RawData">Reset Search</el-button>
      </el-col>

    </el-row>


      <el-row align="middle">
        <el-col>
      <patron_book-data_table v-model="tableData"></patron_book-data_table>
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
  serverPath,
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

  {
    "isDelete": null,
    "createTime": null,
    "updateTime": null,
    "bid": 1604,
    "name": "The Power of Habit",
    "author": "Charles Duhigg",
    "cid": 6,
    "cateName": "Psychology",
    "nums": 4,
    "available": 4,
    "description": "NEW YORK TIMES BESTSELLER ? This instant classic explores how we can change our lives by changing our habits. NAMED ONE OF THE BEST BOOKS OF THE YEAR BY The Wall Street Journal ? Financial Times In The Power of Habit, award-winning business reporter Charles Duhigg takes us to the thrilling edge of scientific discoveries that explain why habits exist and how they can be changed. Distilling vast amounts of information into engrossing narratives that take us from the boardrooms of Procter & Gamble to the sidelines of the NFL to the front lines of the civil rights movement, Duhigg presents a whole new understanding of human nature and its potential. At its core, The Power of Habit contains an exhilarating argument: The key to exercising regularly, losing weight, being more productive, and achieving success is understanding how habits work. As Duhigg shows, by harnessing this new science, we can transform our businesses, our communities, and our lives. With a new Afterword by the author “Sharp, provocative, and useful.”—Jim Collins “Few [books] become essential manuals for business and living. The Power of Habit is an exception. Charles Duhigg not only explains how habits are formed but how to kick bad ones and hang on to the good.”—Financial Times “A flat-out great read.”—David Allen, bestselling author of Getting Things Done: The Art of Stress-Free Productivity “You’ll never look at yourself, your organization, or your world quite the same way.”—Daniel H. Pink, bestselling author of Drive and A Whole New Mind “Entertaining . . . enjoyable . . . fascinating . . . a serious look at the science of habit formation and change.”—The New York Times Book Review",
    "isbn": "9780679603856",
    "publisher": "Random House"
  },
];
const rawData = ref();
const tableData = ref(testBookData);
const cataTypeSelectValue = ref();
const inputValue = ref("");

const reset2RawData = () => {
  tableData.value.length = 0;
  inputValue.value = "";
  cataTypeSelectValue.value = "";
  console.log(rawData.value);
  for (let i = 0; i < rawData.value.length; i++) {
    tableData.value.push(rawData.value[i]);
  }
}

const QueryAllBook = async () => {
  tableData.value.length = 0;
  let tempResult: any;
  axios({
    method: "GET",
    url: serverPath + "book/getBookByPage",
    params: {
      page: 1,
      size: 1,
    }
  }).then(res => {
    tempResult = res.data;
    let totalAmount = tempResult.data.nums;
    axios({
      method: "GET",
      url: serverPath + "book/getBookByPage",
      params: {
        page: 1,
        size: totalAmount,
      }
    }).then(res => {
      for (let i = 0; i < totalAmount; i++) {
        tableData.value.push(res.data.data.book[i]);
      }
      rawData.value = tableData.value.slice();
    });
  });
}

// eslint-disable-next-line no-undef
defineExpose({
  QueryAllBook,
})

QueryAllBook();
const cataQueryFunction = () => {
  axios({
    method: "POST",
    url: serverPath + "book/getBookByCid",
    params: {
      cid: cataTypeSelectValue.value,
    }
  }).then(res => {
    let reciveData = res.data.data;
    tableData.value.length = 0;
    for (let i = 0; i < reciveData.length; i++) {
      tableData.value.push(reciveData[i]);
    }
  });
}

const searchBtnClick = async () => {
  if (inputValue.value === "") {
    ElMessage({
      showClose: true,
      message: "You have to input something to start searching!",
      type: 'error',
    });
    return;
  }
  else{
    let flag = true;
    let i = 1;
    tableData.value.length = 0;
    while (flag) {
      let tempResult: any;
      await
          axios({
            method: "GET",
            url: serverPath + "book/list",
            params: {
              text: inputValue.value,
              pageNum: i,
            }
          }).then(res => {
            tempResult = res.data;
          });
      if (tempResult.code != 200) {
        flag = false;
      } else {
        for (let j = 0; j < tempResult.data.length; j++) {
          tableData.value.push(tempResult.data[j]);
        }
      }
      i += 1;
    }

  }
}

</script>

<style scoped>
.table-col {
  max-width: 1680px;
}
</style>