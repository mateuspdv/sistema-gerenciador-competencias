import { CategoryModel } from './../models/category.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient) { }

  url: string = 'http://localhost:8080/api/categoria';

  findAll(): Observable<CategoryModel[]> {
    return this.httpClient.get<CategoryModel[]>(this.url);
  }

  findById(idCategory: number): Observable<CategoryModel> {
    return this.httpClient.get<CategoryModel>(this.url + '/' + idCategory);
  }

}
