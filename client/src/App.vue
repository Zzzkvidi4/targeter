<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href class="navbar-brand" @click.prevent>Targeter</a>
      <div class="navbar-nav mr-auto">
        <li v-if="currentUser" class="nav-item">
          <router-link v-if="currentUser" to="/targets" class="nav-link">Мои цели</router-link>
        </li>
        <li v-if="currentUser" class="nav-item">
          <router-link v-if="currentUser" to="/target" class="nav-link">Добавить цель</router-link>
        </li>
      </div>

      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/register" class="nav-link">
            <font-awesome-icon icon="user-plus" />Зарегистрироваться
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" />Войти
          </router-link>
        </li>
      </div>

      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" />Выйти
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <router-view />
    </div>
    <v-dialog/>
  </div>
</template>

<script>
    export default {
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },
            showAdminBoard() {
                if (this.currentUser && this.currentUser.roles) {
                    return this.currentUser.roles.includes('ROLE_ADMIN');
                }

                return false;
            },
            showModeratorBoard() {
                if (this.currentUser && this.currentUser.roles) {
                    return this.currentUser.roles.includes('ROLE_MODERATOR');
                }

                return false;
            }
        },
        created() {
          console.log('App is running in (' + process.env.NODE_ENV + ') mode');
        },
        methods: {
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        }
    };
</script>
