import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Candidato } from '../models/candidato';
import { CandidatoRequest } from '../models/candidato-request';

@Injectable({
  providedIn: 'root'
})
export class CandidatoService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/candidatos';

  listar(): Observable<Candidato[]> {
    return this.http.get<Candidato[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<Candidato> {
    return this.http.get<Candidato>(`${this.apiUrl}/${id}`);
  }

  criar(candidato: CandidatoRequest): Observable<Candidato> {
    return this.http.post<Candidato>(this.apiUrl, candidato);
  }

  editar(id: number, candidato: CandidatoRequest): Observable<Candidato> {
    return this.http.put<Candidato>(`${this.apiUrl}/${id}`, candidato);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  votar(id: number): Observable<Candidato> {
    return this.http.post<Candidato>(`${this.apiUrl}/${id}/votar`, {});
  }
}
