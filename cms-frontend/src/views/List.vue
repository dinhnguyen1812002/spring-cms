<template>
    <div class="p-6 bg-white rounded-lg shadow">
        <!-- Header -->
        <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-900">Danh sách Landing Pages</h1>
            <button @click="$router.push('/create')"
                class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center gap-2 transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd"
                        d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                        clip-rule="evenodd" />
                </svg>
                Thêm mới
            </button>
        </div>

        <!-- Alert Messages -->
        <div v-if="message" :class="[
            'mb-4 p-4 rounded-lg',
            message.type === 'success' ? 'bg-green-50 text-green-700' : 'bg-red-50 text-red-700'
        ]">
            {{ message.text }}
        </div>

        <!-- Loading State -->
        <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
        </div>

        <!-- Table -->
        <div v-else class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Tên
                            trang</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Slug
                        </th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Trạng
                            thái</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Ngày
                            tạo</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Thao
                            tác</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <tr v-for="page in paginatedPages" :key="page.id" class="hover:bg-gray-50">
                        <td class="px-6 py-4 whitespace-nowrap">
                            <div class="font-medium text-gray-900">{{ page.name }}</div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <span class="px-2 py-1 text-sm bg-blue-100 text-blue-800 rounded-full">
                                {{ page.slug }}
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <span :class="[
                                'px-2 py-1 text-sm rounded-full',
                                // getStatusClasses(page.status)
                            ]">
                                Public
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                            <!-- {{ formatDate(page.createdAt) }} -->
                              {{ formatDate(new Date().toLocaleDateString()) }}
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm">
                            <div class="flex gap-2">
                                <button @click="$router.push(`/edit/${page.slug}`)"
                                    class="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                                    title="Chỉnh sửa">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20"
                                        fill="currentColor">
                                        <path
                                            d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z" />
                                    </svg>
                                </button>
                                <button @click="openPreview(page)"
                                    class="p-2 text-gray-600 hover:bg-gray-50 rounded-lg transition-colors"
                                    title="Xem trước">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20"
                                        fill="currentColor">
                                        <path d="M10 12a2 2 0 100-4 2 2 0 000 4z" />
                                        <path fill-rule="evenodd"
                                            d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </button>
                                <button @click="confirmDelete(page)"
                                    class="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors" title="Xóa">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20"
                                        fill="currentColor">
                                        <path fill-rule="evenodd"
                                            d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <!-- Pagination -->
            <div class="flex items-center justify-between px-4 py-3 border-t border-gray-200">
                <div class="flex items-center">
                    <span class="text-sm text-gray-700">
                        Hiển thị {{ startIndex + 1 }}-{{ endIndex }} trong {{ pages.length }} kết quả
                    </span>
                </div>
                <div class="flex gap-2">
                    <button @click="currentPage--" :disabled="currentPage === 1" :class="[
                        'px-3 py-1 rounded border',
                        currentPage === 1
                            ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                            : 'bg-white text-gray-700 hover:bg-gray-50'
                    ]">
                        Trước
                    </button>
                    <button @click="currentPage++" :disabled="endIndex >= pages.length" :class="[
                        'px-3 py-1 rounded border',
                        endIndex >= pages.length
                            ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                            : 'bg-white text-gray-700 hover:bg-gray-50'
                    ]">
                        Sau
                    </button>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div v-if="showDeleteModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div class="bg-white rounded-lg p-6 max-w-md w-full mx-4">
                <h3 class="text-lg font-medium mb-4">Xác nhận xóa {{ selectedPage?.name }}</h3>
                <p class="text-gray-600 mb-6">Bạn có chắc chắn muốn xóa landing page này?</p>
                <div class="flex justify-end gap-3">
                    <button @click="showDeleteModal = false" class="px-4 py-2 border rounded-lg hover:bg-gray-50">
                        Hủy
                    </button>
                    <button @click="deletePage" class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700">
                        Xóa
                    </button>
                </div>
            </div>
        </div>

        <!-- Preview Modal -->
        <div v-if="showPreviewModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div class="bg-white rounded-lg p-6 max-w-6xl w-full mx-4 h-5/6">
                <div class="flex justify-between items-center mb-4">
                    <h3 class="text-lg font-medium">Xem trước Landing Page</h3>
                    <button @click="showPreviewModal = false" class="p-2 hover:bg-gray-100 rounded-lg">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd"
                                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                clip-rule="evenodd" />
                        </svg>
                    </button>
                </div>
                <iframe v-if="selectedPage" :src="`http://localhost:8080/page/${selectedPage.slug}`"
                    class="w-full h-full border-0 rounded" />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import type { LandingPage } from '../types/LandingPage';

const router = useRouter();
const pages = ref<LandingPage[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const itemsPerPage = 10;
const showDeleteModal = ref(false);
const showPreviewModal = ref(false);
const selectedPage = ref<LandingPage | null>(null);
const message = ref<{ type: 'success' | 'error'; text: string } | null>(null);

// Computed properties for pagination
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage);
const endIndex = computed(() => Math.min(startIndex.value + itemsPerPage, pages.value.length));
const paginatedPages = computed(() =>
    pages.value.slice(startIndex.value, endIndex.value)
);

// Fetch data
const fetchPages = async () => {
    loading.value = true;
    try {
        const response = await axios.get<LandingPage[]>('http://localhost:8080/api/landing-pages');
        pages.value = response.data;
    } catch (error) {
        showMessage('Không thể tải danh sách landing pages', 'error');
    } finally {
        loading.value = false;
    }
};

// Utilities
const formatDate = (date: string) => {
    return new Date(date).toLocaleDateString('vi-VN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
};

const getStatusClasses = (status: string) => {
    const classes = {
        'published': 'bg-green-100 text-green-800',
        'draft': 'bg-yellow-100 text-yellow-800',
        'archived': 'bg-gray-100 text-gray-800'
    };
    return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800';
};

const showMessage = (text: string, type: 'success' | 'error') => {
    message.value = { text, type };
    setTimeout(() => {
        message.value = null;
    }, 3000);
};

// Modal handlers
const openPreview = (page: LandingPage) => {
    selectedPage.value = page;
    showPreviewModal.value = true;
};

const confirmDelete = (page: LandingPage) => {
    selectedPage.value = page;
    showDeleteModal.value = true;
};

const deletePage = async () => {
    if (!selectedPage.value) return;

    const pageName = selectedPage.value.name; // Lưu lại tên trang trước khi xóa

    try {
        await axios.delete(`http://localhost:8080/api/landing-pages/${selectedPage.value.id}`);
        showMessage(`Đã xóa landing page "${pageName}" thành công`, 'success'); // Hiển thị tên trang đã xóa
        await fetchPages();
    } catch (error) {
        showMessage(`Không thể xóa landing page "${pageName}"`, 'error');
    } finally {
        showDeleteModal.value = false;
        selectedPage.value = null;
    }
};


// Fetch data on mount
onMounted(fetchPages);
</script>