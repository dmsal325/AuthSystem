<template>
  <div>
    <!--<h1>{{ // token }}</h1>-->
     <!--{{ info }}-->
    <table>
      <!--<thead>-->
      <!--<tr>-->
        <!--<th v-for="key in columns"-->
            <!--@click="sortBy(key)"-->
            <!--:class="{ active: sortKey == key }">-->
          <!--{{ key | capitalize }}-->
          <!--<span class="arrow" :class="sortOrders[key] > 0 ? 'asc' : 'dsc'">-->
          <!--</span>-->
        <!--</th>-->
      <!--</tr>-->
      <!--</thead>-->

    <tr id="v-for-users" class="demo">
      <tr v-for="value in object">
        <!--{{ value }}-->
        {{value.user_id}}
        {{value.user_name}}
        {{value.user_age}}
      </tr>
    </tr>
    </table>

    <!--<ul v-for="value in users" :key="user.user_idx">-->
      <!--<li>{{user.user_id}}</li>-->
      <!--<li>{{user.user_name}}</li>-->
      <!--<li>{{user.user_age}}</li>-->
    <!--</ul>-->

    </div>
  <!--<ul v-if="users && users.length">-->
    <!--<li v-for="user of users">-->
      <!--<p>{{user.user_id}}</p>-->
      <!--<p>{{user.user_name}}</p>-->
      <!--<p>{{user.user_age}}</p>-->
    <!--</li>-->
  <!--</ul>-->
  <!--<ul v-if="errors && errors.length">-->
    <!--<li v-for="error of errors">-->
      <!--{{error.message}}-->
    <!--</li>-->
  <!--</ul>-->
</template>

<script>
import axios from 'axios'

export default {
  name: 'Users',
  data () {
    return {
      info: '',
      object: {
        user_idx: '',
        user_id: '',
        user_name: '',
        user_age: ''
      },
      token: localStorage.getItem('token')
    }
  },

  created () {
    axios.get(`http://localhost:8080/users`)
      .then(response => {
        this.info = response.data
        this.object = response.data.data
        console.log(this.object)
      })
      .catch(e => {
        this.errors(e)
      })
  }

}
</script>

<style scoped>
  th {
    background-color: #42b983;
    color: rgba(255,255,255,0.66);
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }

  td {
    background-color: #f9f9f9;
  }

  th, td {
    min-width: 120px;
    padding: 10px 20px;
  }

  th.active {
    color: #fff;
  }

  th.active .arrow {
    opacity: 1;
  }

  .arrow {
    display: inline-block;
    vertical-align: middle;
    width: 0;
    height: 0;
    margin-left: 5px;
    opacity: 0.66;
  }

  .arrow.asc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #fff;
  }

  .arrow.dsc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #fff;
  }
</style>
