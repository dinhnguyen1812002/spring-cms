// services/landingPageService.ts

import type {CreateLandingPage, LandingPage} from '../types/LandingPage';
import apiClient from "../api.ts";

export const getAllLandingPages = async (): Promise<LandingPage[]> => {
    const response = await apiClient.get<LandingPage[]>('/api/landing-pages/latest');
    return response.data;
};

export const deleteLandingPage = async (id: number): Promise<void> => {
    await apiClient.delete(`/api/landing-pages/${id}`);
};

export const getLandingPage = async (slug: string): Promise<LandingPage> => {
    const response = await apiClient.get<LandingPage>(`/api/landing-pages/${slug}`);
    return response.data;
};

export const createLandingPage = async (data: CreateLandingPage) => {
    return await apiClient.post('/api/landing-pages/create', data);
};

export const updateLandingPage = async (slug: string, data: LandingPage): Promise<LandingPage> => {
    const response = await apiClient.put<LandingPage>(`/api/landing-pages/${slug}`, data);
    return response.data;
};