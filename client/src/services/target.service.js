import axios from 'axios';
import authHeader from "./auth-header";

const API_URL = 'http://localhost:8080/api/targets';

class TargetService {
    createTarget(target) {
        return axios.post(API_URL, target, { headers : authHeader()})
    }

    getTargets() {
        return axios.get(API_URL, { headers : authHeader() }).then(response => response.data.data);
    }
}

export default new TargetService();