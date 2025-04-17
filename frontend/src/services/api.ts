import axios from 'axios';
import { GoldRecord } from '../types/GoldRecord';

const API_BASE_URL = '/api';

export const goldRecordApi = {
    async getCurrentGoldPrice() {
        const response = await axios.get(`${API_BASE_URL}/gold/price`);
        return response.data;
    },

    async createRecord(record: GoldRecord, photo?: File) {
        const formData = new FormData();
        formData.append('record', new Blob([JSON.stringify(record)], { type: 'application/json' }));
        if (photo) {
            formData.append('photo', photo);
        }
        const response = await axios.post(`${API_BASE_URL}/gold-records`, formData);
        return response.data;
    },

    async updateRecord(id: number, record: GoldRecord, photo?: File) {
        const formData = new FormData();
        formData.append('record', new Blob([JSON.stringify(record)], { type: 'application/json' }));
        if (photo) {
            formData.append('photo', photo);
        }
        const response = await axios.put(`${API_BASE_URL}/gold-records/${id}`, formData);
        return response.data;
    },

    async deleteRecord(id: number) {
        await axios.delete(`${API_BASE_URL}/gold-records/${id}`);
    },

    async getRecord(id: number) {
        const response = await axios.get(`${API_BASE_URL}/gold-records/${id}`);
        return response.data;
    },

    async getAllRecords(isSummary?: boolean) {
        const response = await axios.get(`${API_BASE_URL}/gold-records`, {
            params: { isSummary }
        });
        return response.data;
    },

    async getRecordsByDateRange(startDate: string, endDate: string) {
        const response = await axios.get(`${API_BASE_URL}/gold-records/by-date-range`, {
            params: { startDate, endDate }
        });
        return response.data;
    },

    async getTodayPrices(isSummary?: boolean) {
        const response = await axios.get(`${API_BASE_URL}/gold/today-prices`, {
            params: { isSummary }
        });
        return response.data;
    },
};
