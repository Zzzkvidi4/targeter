import axios from 'axios';
import authHeader from "./auth-header";

const API_URL = 'http://localhost:8080/api/';

class StatusService {
    getStatuses() {
        return axios.get(API_URL + 'statuses', { headers: authHeader() })
            .then(response => response.data.data);
    }
}

export default new StatusService();