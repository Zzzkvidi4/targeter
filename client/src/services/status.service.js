import axios from 'axios';

const API_URL = 'http://localhost:8080/api/';

class StatusService {
    getStatuses() {
        return axios.get(API_URL + 'statuses')
            .then(response => response.data.data);
    }
}

export default new StatusService();