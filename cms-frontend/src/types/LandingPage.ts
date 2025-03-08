export interface LandingPage {
    id: number;
    name: string;
    slug: string;
    template: string;
    htmlContent: string;
    cssContent: string;
    jsContent: string;
    version?: number;
    isPublished: boolean;
  }
  // types/LandingPage.ts
export interface CreateLandingPage {
  name: string;
  slug: string;
  template: string;
  htmlContent: string;
  cssContent: string;
  jsContent: string;
  version?: number;
  isPublished: boolean;
}
