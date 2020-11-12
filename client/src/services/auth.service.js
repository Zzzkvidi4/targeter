import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'signin', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                console.log(response.data);
                if (response.data.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            username: user.username,
            email: user.email,
            password: user.password
        })
        .then(response => {
            if (response.data.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(response.data.data));
            }

            return response.data;
        });
    }
}

export default new AuthService();