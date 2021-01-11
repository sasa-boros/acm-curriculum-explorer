import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SparqlService {

  constructor(
    private http: HttpClient
  ) {}

  executeStatement(statement: String) {
    return this.http.post(environment.apiUrlPrefix + `/sparql/execute-statement`, statement, {responseType: 'text'});
  }

}
