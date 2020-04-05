import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWord } from 'app/shared/model/word.model';
import { WordService } from './word.service';

@Component({
  selector: 'jhi-word-detail',
  templateUrl: './word-detail.component.html'
})
export class WordDetailComponent implements OnInit {
  word: IWord;
  define: any;
  example: any;
  reference: any;

  constructor(protected activatedRoute: ActivatedRoute, private wordService: WordService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ word }) => {
      this.word = word;
    });
    this.wordService.getDefine(this.word.name).subscribe(rs => {
      this.define = rs.body;
      this.word.definition = this.define.meaning.noun;
      this.word.pronounce = this.define.ipa;
    });
    this.wordService.getExample(this.word.name).subscribe(rs => {
      this.example = rs.body.example[0];
      this.word.example = this.example;
    });
    this.wordService.getReference(this.word.name).subscribe(rs => {
      this.reference = rs.body.relation;
      this.word.synonym = this.reference.synonyms;
    });
  }

  previousState() {
    this.wordService.update(this.word).subscribe(res => {
      window.history.back();
    });
  }
}
