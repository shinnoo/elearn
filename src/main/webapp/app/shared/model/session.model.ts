import { IWord } from 'app/shared/model/word.model';

export interface ISession {
  id?: number;
  sessionNumber?: number;
  scores?: number;
  wrongAnswer?: number;
  words?: IWord[];
  status?: number;
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public sessionNumber?: number,
    public scores?: number,
    public wrongAnswer?: number,
    public words?: IWord[],
    public status?: number
  ) {}
}
