<template>
    <div class="container">
        <header class="jumbotron">
            <h3>
                <strong>{{currentUser.username}}</strong> Профиль
            </h3>
        </header>
        <p>
            <strong>Токен:</strong>
            {{currentUser.accessToken.substring(0, 20)}} ... {{currentUser.accessToken.substr(currentUser.accessToken.length - 20)}}
        </p>
        <p>
            <strong>Идентификатор:</strong>
            {{currentUser.id}}
        </p>
        <strong>Роли:</strong>
        <ul>
            <li v-for="(role,index) in currentUser.roles" :key="index">{{role}}</li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: 'Profile',
        computed: {
            currentUser() {
                console.log('Profile', this.$store, this.$store.state, this.$store.state.auth);
                return this.$store.state.auth.user;
            }
        },
        mounted() {
            if (!this.currentUser) {
                this.$router.push('/login');
            }
        }
    };
</script>