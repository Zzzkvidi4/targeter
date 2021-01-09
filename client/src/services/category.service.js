import axios from 'axios';
import authHeader from "./auth-header";

const API_URL = 'http://localhost:8080/api/categories';

class CategoryService {
    categories = [];

    getCategories() {
        const me = this;
        return axios.get(API_URL + '/all')
            .then(response => {
                me.categories = response.data.data;
                return me.categories;
            });
    }

    createCategory(category) {
        const me = this;
        return axios.post(API_URL, category, { headers: authHeader() })
            .then(response => {
                me.categories.push(response.data.data);
                return me.categories;
            });
    }
}

export default new CategoryService();