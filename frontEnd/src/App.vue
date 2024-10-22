<template>

  <div class="particleBackground">
    <vue-particles
        color="#409EFF"
        :particleOpacity="0.7"
        :particlesNumber="60"
        shapeType="circle"
        :particleSize="6"
        linesColor="#409EFF"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push">
    </vue-particles>
  </div>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow: hidden;
  width: 100%;
}

* {
  margin: 0;
  padding: 0;
  border: 0;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.el-table th.gutter {
  display: table-cell !important;
}

.particleBackground {
  position: absolute;
  z-index: -2147483583;
  width: 100%;
  height: 100%;
}


body::-webkit-scrollbar {
  display: none;
}


</style>


<script lang="ts" setup>
document.body.style.overflow = "hidden";//禁止滚动
window.onbeforeunload = function (e) {
  const storage = window.localStorage;
};

</script>