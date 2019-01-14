<template>
  <div>
    <h2>Signup</h2>
    <form @submit.prevent="sendSignup">
      <input placeholder="Enter your ID" v-model="user.user_id"><br><br>
      <input placeholder="Enter your password" v-model="user.user_pwd"><br><br>
      <input placeholder="Enter your name" v-model="user.user_name"><br><br>
      <input placeholder="Enter your age" v-model="user.user_age"><br><br>
      <button type="submit">회원가입</button>
    </form>

    <!--<h1>{{  info }}</h1>-->
  </div>
</template>

<script>
import axios from 'axios'
const baseURI = localStorage.getItem('baseURI')

export default {
  name: 'SignUp',
  data () {
    return {
      user: {
        user_id: '',
        user_pwd: '',
        user_name: '',
        user_age: 0
      },
      info: ''
    }
  },
  methods: {
    sendSignup: function () {
      console.log(this.user.user_id)
      console.log(this.user.user_pwd)
      console.log(this.user.user_name)
      console.log(this.user.user_age)

      axios.post(`${baseURI}/signup`, this.user)
        .then(response => {
          // debugger
          this.info = response
          location.href = './login'
        }
        ).catch(e => {
          console.log(e)
          this.errors(e)
          location.href = './signup'
        })
    }
  }
}
</script>

<style scoped>

</style>
