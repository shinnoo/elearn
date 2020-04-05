export interface IWord {
  id?: number;
  name?: string;
  definition?: string;
  pronounce?: string;
  form?: string;
  example?: string;
  collocations?: string;
  synonym?: string;
  sessionId?: number;
}

export class Word implements IWord {
  constructor(
    public id?: number,
    public name?: string,
    public definition?: string,
    public pronounce?: string,
    public form?: string,
    public example?: string,
    public collocations?: string,
    public synonym?: string,
    public sessionId?: number
  ) {}
}
