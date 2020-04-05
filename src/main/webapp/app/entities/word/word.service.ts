import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWord } from 'app/shared/model/word.model';

type EntityResponseType = HttpResponse<IWord>;
type EntityArrayResponseType = HttpResponse<IWord[]>;

@Injectable({ providedIn: 'root' })
export class WordService {
  public resourceUrl = SERVER_API_URL + 'api/words';
  public twinwordUrl_define = 'https://twinword-word-graph-dictionary.p.rapidapi.com/definition/';
  public twinwordUrl_example = 'https://twinword-word-graph-dictionary.p.rapidapi.com/example/';
  public twinwordUrl_reference = 'https://twinword-word-graph-dictionary.p.rapidapi.com/reference/';
  public httpOptions = {
    headers: new HttpHeaders({
      'x-rapidapi-host': 'twinword-word-graph-dictionary.p.rapidapi.com',
      'x-rapidapi-key': 'afcc16dc9fmshc93e5c1c328b586p10262fjsn37fe4ed3d4ff'
    })
  };

  constructor(protected http: HttpClient) {}

  create(word: IWord): Observable<EntityResponseType> {
    return this.http.post<IWord>(this.resourceUrl, word, { observe: 'response' });
  }

  update(word: IWord): Observable<EntityResponseType> {
    return this.http.put<IWord>(this.resourceUrl, word, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWord[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDefine(word: string): Observable<HttpResponse<any>> {
    word = word.trim();
    let options: HttpParams = new HttpParams();
    options = word ? options.set('entry', word) : options;
    return this.http.get<Object[]>(this.twinwordUrl_define, { params: options, observe: 'response', headers: this.httpOptions.headers });
  }

  getExample(word: string): Observable<HttpResponse<any>> {
    word = word.trim();
    let options: HttpParams = new HttpParams();
    options = word ? options.set('entry', word) : options;
    return this.http.get<Object[]>(this.twinwordUrl_example, { params: options, observe: 'response', headers: this.httpOptions.headers });
  }

  getReference(word: string): Observable<HttpResponse<any>> {
    word = word.trim();
    let options: HttpParams = new HttpParams();
    options = word ? options.set('entry', word) : options;
    return this.http.get<Object[]>(this.twinwordUrl_reference, { params: options, observe: 'response', headers: this.httpOptions.headers });
  }
}
