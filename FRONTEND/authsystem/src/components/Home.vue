<template>
  <div>
    <h1>{{ msg }}</h1>

    <!--<h1>LogIn</h1>-->
    <!--<form @submit="onSubmit">-->
      <!--<input placeholder="Enter your ID" v-model="user.user_id"><br><br>-->
      <!--<input placeholder="Enter your password" v-model="user.user_pwd"><br><br>-->
      <!--<button type="submit">로그인</button>-->
    <!--</form>-->

    <!--<h1>{{ info }}</h1>-->

  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Home',
  data () {
    return {
      msg: 'Hello World!',
      user: {
        user_id: '',
        user_pwd: ''
      },
      info: ''
    }
  },
  methods: {
    onSubmit: function () {
      // debugger
      console.log(this.user.user_id)
      console.log(this.user.user_pwd)

      const baseURI = 'http://localhost:8080'
      axios.post(`${baseURI}/signin`, this.user)
        .then(response => {
          this.info = response
        }
        ).catch(e => {
          console.log(e)
          this.errors(e)
        })

      // $.ajax({
      //   dataType: 'json',
      //   contentType: 'application/json'
      //   // this.posts = result.data
      // })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }
</style>
