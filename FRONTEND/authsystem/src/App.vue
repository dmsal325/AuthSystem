<template>
  <div id="app">
    <img src="./assets/logo.png">
    <h1>{{ msg }}</h1>
    <router-view/>
    <h3><router-link to="/">홈</router-link></h3>
    <h3><router-link to="/login">로그인</router-link></h3>
    <h3><router-link to="/signup">회원가입</router-link></h3>
  </div>
</template>

<script>
export default {
  name: 'App'
}

localStorage.setItem('baseURI', 'http://localhost:8080')
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
