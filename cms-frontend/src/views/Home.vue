<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Navigation -->
    <nav class="bg-white shadow">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16 items-center">
          <div class="flex-shrink-0">
            <h1 class="text-xl font-bold text-indigo-600">Company Name</h1>
          </div>
          <div class="hidden md:block">
            <div class="ml-10 flex items-center space-x-4">
              <a href="#" class="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md">Trang chủ</a>
              <router-link :to="{ name: 'list' }"  class="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md">Page</router-link>
              <a href="#" class="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md">Về chúng tôi</a>
              <a href="#" class="text-gray-700 hover:text-indigo-600 px-3 py-2 rounded-md">Liên hệ</a>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <!-- Hero Section -->
    <div class="relative bg-white overflow-hidden">
      
      <div class="max-w-7xl mx-auto">
        <div class="relative z-10 pb-8 bg-white sm:pb-16 md:pb-20 lg:pb-28 xl:pb-32">
          <main class="mt-10 mx-auto max-w-7xl px-4 sm:mt-12 sm:px-6 lg:mt-16 lg:px-8 xl:mt-20">
            <div class="text-center">
              <!-- Animated Heading -->
              <h1 class="text-4xl tracking-tight font-extrabold text-gray-900 sm:text-5xl md:text-6xl 
                         opacity-0 translate-y-4 animate-fade-in-up">
                <span class="block transition-transform duration-700 delay-100 
                           hover:scale-105">Giải pháp tối ưu</span>
                <span class="block text-indigo-600 transition-transform duration-700 delay-300 
                           hover:scale-105">cho doanh nghiệp của bạn</span>
              </h1>

              <!-- Animated Description -->
              <p class="mt-3 text-base text-gray-500 sm:mt-5 sm:text-lg sm:max-w-xl sm:mx-auto md:mt-5 md:text-xl
                        opacity-0 translate-y-4 animate-fade-in-up animation-delay-500">
                Chúng tôi cung cấp các giải pháp công nghệ hiện đại giúp doanh nghiệp của bạn phát triển nhanh chóng và hiệu quả.
              </p>

              <!-- Animated Buttons -->
              <div class="mt-5 sm:mt-8 sm:flex sm:justify-center opacity-0 animate-fade-in animation-delay-700">
                <div class="rounded-md shadow transition-transform duration-300 hover:scale-105">
                  <a href="#" class="w-full flex items-center justify-center px-8 py-3 border border-transparent 
                                   text-base font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 
                                   md:py-4 md:text-lg md:px-10 transition-colors duration-300 
                                   transform hover:-translate-y-1">
                    Bắt đầu ngay
                  </a>
                </div>
                <div class="mt-3 sm:mt-0 sm:ml-3 transition-transform duration-300 hover:scale-105">
                  <a href="#" class="w-full flex items-center justify-center px-8 py-3 border border-transparent 
                                   text-base font-medium rounded-md text-indigo-600 bg-indigo-100 hover:bg-indigo-200 
                                   md:py-4 md:text-lg md:px-10 transition-colors duration-300 
                                   transform hover:-translate-y-1">
                    Tìm hiểu thêm
                  </a>
                </div>
              </div>

              <!-- Decorative Elements -->
              <div class="absolute top-0 right-0 -mt-20 opacity-10 animate-float">
                <div class="w-48 h-48 bg-indigo-500 rounded-full blur-xl"></div>
              </div>
              <div class="absolute bottom-0 left-0 -mb-20 opacity-10 animate-float-delayed">
                <div class="w-72 h-72 bg-blue-500 rounded-full blur-xl"></div>
              </div>
            </div>
          </main>
        </div>
      </div>
    </div>

    <!-- Features Section -->
    <div class="py-12 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center">
          <h2 class="text-3xl font-extrabold text-gray-900 sm:text-4xl">
            Dịch vụ của chúng tôi
          </h2>
          <p class="mt-4 text-lg text-gray-500">
            Những giải pháp tốt nhất cho doanh nghiệp của bạn
          </p>
        </div>

        <div class="mt-10">
          <div class="grid grid-cols-1 gap-10 sm:grid-cols-2 lg:grid-cols-3">
            <div class="p-6 bg-gray-50 rounded-lg">
              <h3 class="text-lg font-medium text-gray-900">Giải pháp 1</h3>
              <p class="mt-2 text-base text-gray-500">
                Mô tả chi tiết về giải pháp và lợi ích mà nó mang lại cho khách hàng.
              </p>
            </div>
            <div class="p-6 bg-gray-50 rounded-lg">
              <h3 class="text-lg font-medium text-gray-900">Giải pháp 2</h3>
              <p class="mt-2 text-base text-gray-500">
                Mô tả chi tiết về giải pháp và lợi ích mà nó mang lại cho khách hàng.
              </p>
            </div>
            <div class="p-6 bg-gray-50 rounded-lg">
              <h3 class="text-lg font-medium text-gray-900">Giải pháp 3</h3>
              <p class="mt-2 text-base text-gray-500">
                Mô tả chi tiết về giải pháp và lợi ích mà nó mang lại cho khách hàng.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <footer class="bg-gray-800">
      <div class="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <div class="text-center">
          <p class="text-base text-gray-400">
            © {{new Date().getFullYear()}} Ng-dev. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'LandingPage',
  setup() {
    return {}
  }
})
</script>

<style scoped>
@reference "tailwindcss";
/* Base Animated Elements */
.circle-1,
.circle-2,
.circle-3 {
  @apply absolute rounded-full opacity-30;
  background: linear-gradient(45deg, #6366f1, #a5b4fc);
  filter: blur(50px);
}

.line-1,
.line-2,
.line-3 {
  @apply absolute h-px;
  background: linear-gradient(90deg, transparent, #6366f1, transparent);
}

/* Positioning and Sizing */
.circle-1 {
  @apply top-0 -left-20;
  width: 400px;
  height: 400px;
}

.circle-2 {
  @apply top-1/2 -right-20;
  width: 300px;
  height: 300px;
}

.circle-3 {
  @apply -bottom-20 left-1/3;
  width: 200px;
  height: 200px;
}

.line-1 {
  @apply top-1/4 -left-full w-full;
}

.line-2 {
  @apply top-1/2 -left-full w-150;
}

.line-3 {
  @apply top-3/4 -left-full w-120;
}

/* Animations */
@keyframes float {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(20px, 20px); }
}

@keyframes slide {
  from { transform: translateX(-100%); }
  to { transform: translateX(100%); }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-float {
  animation: float 20s infinite ease-in-out;
}

.animate-float-delayed {
  animation: float 20s infinite ease-in-out;
  animation-delay: 10s;
}

.animate-slide {
  animation: slide 15s infinite linear;
}

.animate-slide-delayed {
  animation: slide 15s infinite linear;
  animation-delay: 7s;
}

.animate-fade-in-up {
  animation: fadeInUp 1s ease-out forwards;
}

.animation-delay-200 {
  animation-delay: 0.2s;
}

.animation-delay-400 {
  animation-delay: 0.4s;
}
</style>
