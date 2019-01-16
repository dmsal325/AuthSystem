 <template>
 <div>
     <button @submit.prevent="hello" type="submit">Hello</button>
     <h1>{{  info.data }}</h1>
 </div>

 </template>
<script>
import axios from 'axios'
// const baseURI = localStorage.getItem('baseURI')

export default {
  data () {
    return {
      info: ''
    }
  },
  created () {
    let token = localStorage.getItem('token')
    if (!token) {
      location.href = './login'
    }
    this.fetchData(token)
  },
  methods: {
    fetchData (token) {
      axios({
        method: 'get',
        url: 'http://localhost:8082/hello',
        headers: { 'token': `${token}` }
      })
        .then(response => {
          this.info = response.data
        })
        .catch(error => {
          if (error.response.status === 402) {
            localStorage.removeItem('token')
            location.href = './login'
          }
          // handle error
        })
      // axios.get(`${baseURI}/hello`)
      //   .then(response => {
      //     // debugger
      //     this.info = response.data
      //     localStorage.getItem('token')
      //     console.log(this.info.data.token)
      //     location.href = './hello'
      //   }
      //   ).catch(e => {
      //     console.log(e)
      //     this.errors(e)
      //     location.href = './home'
      //   })
    }
  }
}
</script>
<style scoped>
</style>
