<template>
    <div class="col-md-12">
        <div class="card card-container">
            <img
                    id="profile-img"
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    class="profile-img-card"
            />
            <form name="form" @submit.prevent="handleLogin">
                <div class="form-group">
                    <label for="username">Логин</label>
                    <input
                            v-model="user.username"
                            v-validate="'required'"
                            type="text"
                            class="form-control"
                            name="username"
                    />
                    <div
                            v-if="errors.has('username')"
                            class="alert alert-danger"
                            role="alert"
                    >Логин обязателен!</div>
                </div>
                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input
                            v-model="user.password"
                            v-validate="'required'"
                            type="password"
                            class="form-control"
                            name="password"
                    />
                    <div
                            v-if="errors.has('password')"
                            class="alert alert-danger"
                            role="alert"
                    >Пароль обязателен!</div>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span>Войти</span>
                    </button>
                </div>
                <div class="form-group">
                    <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                </div>
                <a :href="redirectUrl">Войти через VK</a>
            </form>
        </div>
    </div>
</template>



<script>
    import User from '../models/user';

    export default {
        name: 'Login',
        data() {
            return {
                user: new User('', ''),
                loading: false,
                message: '',
                redirectUrl : process.env.NODE_ENV === "development"
                    ? "https://oauth.vk.com/authorize?client_id=7662595&display=page&redirect_uri=http://localhost:8081/login&scope=friends&response_type=code&v=5.126"
                    : "https://oauth.vk.com/authorize?client_id=7662595&display=page&redirect_uri=http://localhost:8080/login&scope=friends&response_type=code&v=5.126"
                    //: "https://oauth.vk.com/authorize?client_id=7662595&display=page&redirect_uri=http://35.238.94.215/login&scope=friends&response_type=code&v=5.126"
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/profile');
            }
            if (this.$route.query.code) {
                this.loading = true;
                this.$store.dispatch('auth/loginVk', {code : this.$route.query.code})
                    .then(
                        () => {
                            this.$router.push('/profile');
                        },
                        () => {
                            this.loading = false;
                        }
                    );
            }
        },
        methods: {
            handleLogin() {
                this.loading = true;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }

                    if (this.user.username && this.user.password) {
                        this.$store.dispatch('auth/login', this.user).then(
                            () => {
                                this.$router.push('/profile');
                            },
                            error => {
                                this.loading = false;
                                this.message =
                                    (error.response && error.response.data) ||
                                    error.message ||
                                    error.toString();
                            }
                        );
                    }
                });
            }
        }
    };
</script>

<style scoped>
    label {
        display: block;
        margin-top: 10px;
    }

    .card-container.card {
        max-width: 350px !important;
        padding: 40px 40px;
    }

    .card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px;
        margin: 0 auto 25px;
        margin-top: 50px;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    }

    .profile-img-card {
        width: 96px;
        height: 96px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }
</style>