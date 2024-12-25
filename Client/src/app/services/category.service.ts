import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../core/models/ApiResponse';
import { Category } from '../core/models/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiUrl = '/api/categories';

  constructor(private http: HttpClient) {}

  createCategory(category: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.apiUrl, category);
  }

  updateCategory(id: number, category: Category): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/${id}`, category);
  }

  updateCategoryChildren(payload: any): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(`${this.apiUrl}/update-children`, payload);
  }

  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/${id}`);
  }

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiUrl);
  }

  deleteCategory(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(`${this.apiUrl}/${id}`);
  }  

  filterCategories(isRacine: boolean, startDate: string | null, endDate: string | null): Observable<Category[]> {
    let params = new HttpParams().set('isRacine', isRacine.toString());
    if (startDate) {
        params = params.set('startDate', startDate);
    }
    if (endDate) {
        params = params.set('endDate', endDate);
    }
    return this.http.get<Category[]>(`${this.apiUrl}/filter`, { params });
}

}
