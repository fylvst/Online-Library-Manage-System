<template>


  <div class="container">

<!--    <div class="teamMember">
      <el-row>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
        <el-col :span="6">
          <div class="photo">
            teamMember Photo Placeholder
          </div>
          <p>Member Name</p>
        </el-col>
      </el-row>


    </div>-->


    <div class="loginWindow">
      <loginComponets></loginComponets>
    </div>

  </div>


</template>

<script lang="ts" setup>
import LoginComponets from "@/components/loginComponets.vue";
</script>

<style scoped>


.loginWindow {
  position: absolute;
  top: 15%;
  right: 10%;
  box-shadow: 2px 2px 5px #000;
  border-radius: 40px;
  background-color: rgba(255, 255, 255, 0.7);

}

.teamMember {
  position: absolute;
  width: 600px;
  height: 500px;
  top: 5%;
  left: 2%;
  box-shadow: 2px 2px 5px #000;
  border-radius: 40px;
  background-color: rgba(255, 255, 255, 0.7);
}

.photo {
  margin-left: 25px;
  margin-top: 20px;
  margin-bottom: 20px;
  width: 60px;
  height: 50px;
  background-color: rgba(0, 255, 0, 0.7);
}

.container {
  position: absolute;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-image: url("../assets/background.jpg");
}


</style>