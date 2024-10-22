<template>

  <div class="container">
    <el-dialog width="600px" @close="dialogCloseEvent" :model-value="props.modelValue.editInfoVisi"
               title="New Book Information Input">


      <el-form
          :label-position="'left'"
          label-width="120px"
          style="max-width: 600px"
      >

        <el-form-item label="book name:">
          <el-input v-model="bookNameInput"/>
        </el-form-item>

        <el-form-item label="location:">
          <el-input v-model="bookLocationInput"/>
        </el-form-item>

        <el-form-item label="author:">
          <el-input v-model="authorInput"/>
        </el-form-item>

        <el-form-item label="cate:">
          <el-select v-model="cataTypeSelect"
                     style="width: 100%">
            <el-option
                v-for="item in bookCategorySelectChoices"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="nums:">
          <el-input-number v-model="numsInput" style="width: 100%" min="1"/>
        </el-form-item>

        <el-form-item label="isbn:">
          <el-input v-model="isbnInput"/>
        </el-form-item>

        <el-form-item label="publisher:">
          <el-input v-model="publisherInput"/>
        </el-form-item>

        <el-form-item label="description:">
          <el-input v-model="descriptionInput" type="textarea" :autosize="{ minRows: 2, maxRows: 6 }"/>
        </el-form-item>
        <el-button @click="findBookDetail">matching by name</el-button>
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
        "value": receiveData[i].cid,
        "label": receiveData[i].cateName
      })
    }
  });
}
getBookSelectChoice();


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
const authorInput = ref();
const cataTypeSelect = ref("");
const numsInput = ref(1);
const isbnInput = ref();
const publisherInput = ref();
const descriptionInput = ref();
const bookLocationInput = ref();

const resetInputValue = () => {
  bookNameInput.value = "";
  bookLocationInput.value = "";
  authorInput.value = "";
  cataTypeSelect.value = "";
  numsInput.value = 1;
  isbnInput.value = "";
  publisherInput.value = "";
  descriptionInput.value = "";
}

const dialogCloseEvent = () => {
  // eslint-disable-next-line vue/no-mutating-props
  props.modelValue.editInfoVisi = false;
  resetInputValue();
}

const findBookDetail = () => {
  let inputName = bookNameInput.value;
  axios({
    method: "GET",
    url: 'https://book-finder1.p.rapidapi.com/api/search',
    params: {
      title: inputName,
    },
    headers: {
      'content-type': 'application/octet-stream',
      'X-RapidAPI-Key': '60994b5995mshea2f7339ed46f31p1c97b7jsn11efc8bf5cb7',
      'X-RapidAPI-Host': 'book-finder1.p.rapidapi.com'
    }
  }).then(res => {
    let ResultData = res.data.results[0];
    authorInput.value = ResultData.author_first_names + " " + ResultData.author_last_names;
    isbnInput.value = ResultData.published_works[0].isbn;
    descriptionInput.value = ResultData.summary;
    publisherInput.value = ResultData.published_works[0].binding;
    bookNameInput.value = ResultData.title;
    cataTypeSelect.value = ResultData.categories[0];
  });

}


const uploadNewBook = () => {
  let cateName: any;
  let cidInput: any;
  if (Number.isInteger(cataTypeSelect.value)) {
    let i = 0;
    for(i = 0;i<bookCategorySelectChoices.value.length;i++)
    {
      if(bookCategorySelectChoices.value[i].value == Number(cataTypeSelect.value)){
        cateName = bookCategorySelectChoices.value[i];
        cidInput = cataTypeSelect.value;
        break;
      }
    }
    if(i>=bookCategorySelectChoices.value.length)
    {
      cidInput = Math.floor(Math.random() * (bookCategorySelectChoices.value.length-3));
      cateName = bookCategorySelectChoices.value[Number(cidInput) - 1];
    }
  } else {
    cidInput = Math.floor(Math.random() * (bookCategorySelectChoices.value.length-3));
    cateName = bookCategorySelectChoices.value[Number(cidInput) - 1];
  }
  if (bookNameInput.value == "" || authorInput.value == "" || cataTypeSelect.value == "") {
    ElMessage({
      showClose: true,
      message: 'Incomplete Input!!!',
      type: 'warning',
    });
  } else {
    let transData = {
      name: bookNameInput.value,
      author: authorInput.value,
      cid: cidInput,
      cateName: cateName.label,
      nums: numsInput.value,
      available: numsInput.value,
      location: bookLocationInput.value,
    };
    if (descriptionInput.value != "") {
      transData.description = descriptionInput.value;
    }
    if (isbnInput.value != "") {
      transData.isbn = isbnInput.value;
    }
    if (publisherInput.value != "") {
      transData.publisher = publisherInput.value;
    }
    axios({
      method: "POST",
      url: serverPath + "book/add",
      params: transData,
    }).then(res => {
      if (res.data.code == 200) {
        ElMessage({
          showClose: true,
          message: "adding book succeed",
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