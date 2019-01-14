<template>
  <div>
    <h1>LogIn</h1>
    <form @submit.prevent="login">
      <input placeholder="Enter your ID" v-model="user.user_id"><br><br>
      <input placeholder="Enter your password" v-model="user.user_pwd"><br><br>
      <button type="submit">로그인</button>
    </form>

    <!--<h1>{{  info }}</h1>-->
  </div>
</template>

<script>
import axios from 'axios'
const baseURI = localStorage.getItem('baseURI')

export default {
  name: 'Login',
  data () {
    return {
      user: {
        user_id: '',
        user_pwd: ''
      },
      info: ''
    }
  },
  methods: {
    login: function () {
      axios.post(`${baseURI}/signin`, this.user)
        .then(response => {
          // debugger
          this.info = response.data
          localStorage.setItem('token', this.info.data.token)
          console.log(this.info.data.token)
          location.href = './home'
        }
        ).catch(e => {
          console.log(e)
          this.errors(e)
          location.href = './login'
        })
    }
  }
}
</script>

<style scoped>

</style>
