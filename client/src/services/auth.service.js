import axios from 'axios';

const API_URL = '/api/auth/';

class AuthService {
    login(user, method) {
        return axios
            .post(API_URL + 'signin', {
                username: user.username,
                password: user.password,
                code: user.code,
                method: method
            })
            .then(response => {
                console.log(response.data);
                if (response.data.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data.data));
                }

                return response.data.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            username: user.username,
            password: user.password
        })
        .then(response => {
            if (response.data.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(response.data.data));
            }

            return response.data.data;
        });
    }
}

export default new AuthService();