<template>
  <div class="min-h-screen bg-gray-50 p-4 sm:p-6 lg:p-8">
    <!-- Header Section -->
    <div class="mb-8">
      <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-6">
        <div>
          <h1 class="text-2xl sm:text-3xl font-bold text-gray-900">Landing Pages</h1>
          <p class="mt-1 text-sm text-gray-500">Manage your landing pages and track their performance</p>
        </div>
        <div class="flex flex-col sm:flex-row gap-3 w-full sm:w-auto">
          <div class="relative flex-1 max-w-md">
            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <svg class="h-5 w-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </div>
            <input
              v-model="searchQuery"
              type="search"
              placeholder="Search pages..."
              class="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg bg-white shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              @input="handleSearch"
            >
          </div>
          <button
            @click="$router.push('/create')"
            class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
          >
            <svg class="-ml-1 mr-2 h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd" />
            </svg>
            New Page
          </button>
        </div>
      </div>

      <!-- Alert Messages -->
      <transition
        enter-active-class="transition-opacity duration-300"
        enter-from-class="opacity-0"
        enter-to-class="opacity-100"
        leave-active-class="transition-opacity duration-300"
        leave-from-class="opacity-100"
        leave-to-class="opacity-0"
      >
        <div
          v-if="message"
          class="rounded-md p-4 mb-6"
          :class="{
            'bg-green-50': message.type === 'success',
            'bg-red-50': message.type === 'error'
          }"
        >
          <div class="flex">
            <div class="flex-shrink-0">
              <svg
                v-if="message.type === 'success'"
                class="h-5 w-5 text-green-400"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fill-rule="evenodd"
                  d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                  clip-rule="evenodd"
                />
              </svg>
              <svg
                v-else
                class="h-5 w-5 text-red-400"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fill-rule="evenodd"
                  d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 0L6 8.586 4.707 7.293a1 1 0 00-1.414 1.414L4.586 10l-1.293 1.293a1 1 0 001.414 1.414L6 11.414l1.293 1.293a1 1 0 001.414-1.414L7.414 10l1.293-1.293a1 1 0 000-1.414z"
                  clip-rule="evenodd"
                />
              </svg>
            </div>
            <div class="ml-3">
              <p
                class="text-sm font-medium"
                :class="{
                  'text-green-800': message.type === 'success',
                  'text-red-800': message.type === 'error'
                }"
              >
                {{ message.text }}
              </p>
            </div>
            <div class="ml-auto pl-3">
              <div class="-mx-1.5 -my-1.5">
                <button
                  type="button"
                  class="inline-flex rounded-md p-1.5 focus:outline-none focus:ring-2 focus:ring-offset-2"
                  :class="{
                    'bg-green-50 text-green-500 hover:bg-green-100 focus:ring-green-600': message.type === 'success',
                    'bg-red-50 text-red-500 hover:bg-red-100 focus:ring-red-600': message.type === 'error'
                  }"
                  @click="message = null"
                >
                  <span class="sr-only">Dismiss</span>
                  <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path
                      fill-rule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>

    <!-- Content Area -->
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
      <!-- Loading State -->
      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-600 border-t-transparent"></div>
      </div>

      <!-- Page List -->
      <div v-else class="divide-y divide-gray-200">
        <!-- Empty State -->
        <div v-if="filteredPages.length === 0" class="text-center py-16 px-4 sm:px-6 lg:px-8">
          <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <h3 class="mt-2 text-lg font-medium text-gray-900">No pages found</h3>
          <p class="mt-1 text-sm text-gray-500">Get started by creating a new page.</p>
          <div class="mt-6">
            <button
              @click="$router.push('/create')"
              type="button"
              class="inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              <svg class="-ml-1 mr-2 h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd" />
              </svg>
              New Page
            </button>
          </div>
        </div>

        <!-- Pages List -->
        <div v-else class="divide-y divide-gray-200">
          <!-- Table Header -->
          <div class="hidden sm:block">
            <div class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              <div class="grid grid-cols-12 gap-4">
                <div class="col-span-4">Name</div>
                <div class="col-span-2">Status</div>
                <div class="col-span-3">Last Updated</div>
                <div class="col-span-3 text-right">Actions</div>
              </div>
            </div>
          </div>

          <!-- Page Items -->
          <div v-for="page in paginatedPages" :key="page.id" class="bg-white hover:bg-gray-50">
            <div class="px-6 py-4">
              <div class="grid grid-cols-1 sm:grid-cols-12 gap-4 items-center">
                <!-- Name -->
                <div class="col-span-4">
                  <div class="flex items-center">
                    <div class="flex-shrink-0 h-10 w-10 rounded-md bg-blue-100 flex items-center justify-center text-blue-600">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path fill-rule="evenodd" d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4z" clip-rule="evenodd" />
                      </svg>
                    </div>
                    <div class="ml-4">
                      <div class="text-sm font-medium text-gray-900 truncate">{{ page.name || 'Untitled Page' }}</div>
                      <div class="text-sm text-gray-500">{{ page.slug || 'No slug' }}</div>
                    </div>
                  </div>
                </div>

                <!-- Status -->
                <div class="col-span-2">
                  <span 
                    class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full"
                    :class="{
                      'bg-green-100 text-green-800': page.isPublished,
                      'bg-yellow-100 text-yellow-800': !page.isPublished
                    }"
                  >
                    {{ page.isPublished ? 'Published' : 'Draft' }}
                  </span>
                </div>

                <!-- Last Updated -->
                <div class="col-span-3">
                  <div class="text-sm text-gray-900">{{ formatDate(page.updatedAt || new Date().toISOString()) }}</div>
                  <div class="text-sm text-gray-500">Created {{ formatDate(page.createdAt || new Date().toISOString()) }}</div>
                </div>

                <!-- Actions -->
                <div class="col-span-3 sm:text-right">

                  <div class="flex justify-end space-x-2">
                    <a class="text-gray-400 hover:text-gray-500 p-1 rounded-full"
                    :href="page.url"
                    >
                      view
                    </a>
                    <button
                      @click="openPreview(page)"
                      class="text-gray-400 hover:text-gray-500 p-1 rounded-full"
                      title="Preview"
                    >
                      <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                      </svg>
                    </button>

                    <a
                      :href="`/edit/${page.id}`"
                      class="text-gray-400 hover:text-gray-500 p-1 rounded-full"
                      title="Edit"
                    >
                      <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                      </svg>
                    </a>
                    <button
                      @click="confirmDelete(page)"
                      class="text-red-400 hover:text-red-500 p-1 rounded-full"
                      title="Delete"
                    >
                      <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="filteredPages.length > 0" class="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
          <div class="flex-1 flex justify-between sm:hidden">
            <button
              @click="currentPage = Math.max(1, currentPage - 1)"
              :disabled="currentPage === 1"
              :class="{'opacity-50 cursor-not-allowed': currentPage === 1}"
              class="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
            >
              Previous
            </button>
            <button
              @click="currentPage = Math.min(totalPages, currentPage + 1)"
              :disabled="currentPage === totalPages"
              :class="{'opacity-50 cursor-not-allowed': currentPage === totalPages}"
              class="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
            >
              Next
            </button>
          </div>
          <div class="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
            <div>
              <p class="text-sm text-gray-700">
                Showing <span class="font-medium">{{ (currentPage - 1) * itemsPerPage + 1 }}</span>
                to <span class="font-medium">{{ Math.min(currentPage * itemsPerPage, filteredPages.length) }}</span>
                of <span class="font-medium">{{ filteredPages.length }}</span> results
              </p>
            </div>
            <div>
              <nav class="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                <button
                  @click="currentPage = Math.max(1, currentPage - 1)"
                  :disabled="currentPage === 1"
                  :class="{
                    'opacity-50 cursor-not-allowed': currentPage === 1,
                    'hover:bg-gray-50': currentPage > 1
                  }"
                  class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500"
                >
                  <span class="sr-only">Previous</span>
                  <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" />
                  </svg>
                </button>
                
                <template v-for="pageNum in visiblePageNumbers" :key="pageNum">
                  <button
                    v-if="pageNum === '...'"
                    disabled
                    class="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700"
                  >
                    ...
                  </button>
                  <button
                    v-else
                    @click="currentPage = pageNum"
                    :class="{
                      'z-10 bg-blue-50 border-blue-500 text-blue-600': pageNum === currentPage,
                      'bg-white border-gray-300 text-gray-500 hover:bg-gray-50': pageNum !== currentPage
                    }"
                    class="relative inline-flex items-center px-4 py-2 border text-sm font-medium"
                  >
                    {{ pageNum }}
                  </button>
                </template>
                
                <button
                  @click="currentPage = Math.min(totalPages, currentPage + 1)"
                  :disabled="currentPage === totalPages"
                  :class="{
                    'opacity-50 cursor-not-allowed': currentPage === totalPages,
                    'hover:bg-gray-50': currentPage < totalPages
                  }"
                  class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500"
                >
                  <span class="sr-only">Next</span>
                  <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" />
                  </svg>
                </button>
              </nav>
            </div>
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
          <iframe v-if="selectedPage" :src="`http://localhost:9000/page/${selectedPage.slug}`"
            class="w-full h-full border-0 rounded" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';

import type { LandingPage } from '../types/LandingPage';
import { deleteLandingPage, getAllLandingPages } from "../services/PageServices.ts";

const router = useRouter();
const pages = ref<LandingPage[]>([]);
const filteredPages = ref<LandingPage[]>([]);
const loading = ref(false);
const searchQuery = ref('');
const currentPage = ref(1);
const itemsPerPage = 10;
const showDeleteModal = ref(false);
const showPreviewModal = ref(false);
const selectedPage = ref<LandingPage | null>(null);
const message = ref<{ type: 'success' | 'error'; text: string } | null>(null);

// Debounce search
let searchTimeout: number | null = null;

// Computed properties
const totalPages = computed(() => Math.ceil(filteredPages.value.length / itemsPerPage));
const paginatedPages = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return filteredPages.value.slice(start, end);
});

const visiblePageNumbers = computed(() => {
  const total = totalPages.value;
  const current = currentPage.value;
  const delta = 2;
  const range = [];
  
  for (let i = Math.max(2, current - delta); i <= Math.min(total - 1, current + delta); i++) {
    range.push(i);
  }
  
  if (current - delta > 2) {
    range.unshift('...');
  }
  if (current + delta < total - 1) {
    range.push('...');
  }
  
  return [1, ...range, total].filter((value, index, self) => {
    return self.indexOf(value) === index;
  });
});

// Methods
const fetchPages = async () => {
  loading.value = true;
  try {
    const data = await getAllLandingPages();
    pages.value = data;
    filterPages();
  } catch (error) {
    console.error('Error fetching pages:', error);
    showMessage('Failed to load pages. Please try again.', 'error');
  } finally {
    loading.value = false;
  }
};

const filterPages = () => {
  if (!searchQuery.value.trim()) {
    filteredPages.value = [...pages.value];
  } else {
    const query = searchQuery.value.toLowerCase().trim();
    filteredPages.value = pages.value.filter(page => 
      (page.name?.toLowerCase().includes(query)) ||
      (page.slug?.toLowerCase().includes(query)) ||
      (page.htmlContent?.toLowerCase().includes(query))
    );
  }
  currentPage.value = 1; // Reset to first page when filtering
};

const handleSearch = () => {
  if (searchTimeout) {
    clearTimeout(searchTimeout);
  }
  
  searchTimeout = window.setTimeout(() => {
    filterPages();
  }, 300);
};

const formatDate = (dateString: string) => {
  if (!dateString) return 'N/A';
  
  const date = new Date(dateString);
  if (isNaN(date.getTime())) return 'Invalid date';
  
  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date);
};

const showMessage = (text: string, type: 'success' | 'error') => {
  message.value = { text, type };
  setTimeout(() => {
    message.value = null;
  }, 5000);
};

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
  
  const pageId = selectedPage.value.id;
  
  try {
    await deleteLandingPage(pageId);
    await fetchPages();
    showMessage('Page deleted successfully', 'success');
  } catch (error) {
    console.error('Error deleting page:', error);
    showMessage('Failed to delete page. Please try again.', 'error');
  } finally {
    showDeleteModal.value = false;
    selectedPage.value = null;
  }
};

const openPage = (url: string) => {
  window.open(url, '_blank', 'noopener,noreferrer');
};

// Watch for changes
watch(searchQuery, () => {
  handleSearch();
});

// Initialize
onMounted(() => {
  fetchPages();
});
</script>
