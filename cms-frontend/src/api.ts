import axios, { type AxiosInstance } from "axios"


// const baseurl = 'http://localhost:9000';
const baseurl = import.meta.env.VITE_API_BASE_URL;
const apiClient:AxiosInstance =axios.create({
    baseURL: baseurl,
    headers: {
        "Content-type": "application/json",
    },
});
export default apiClient;

