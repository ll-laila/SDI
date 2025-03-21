export interface IDB {
  id?: number;
  name?: string;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class DB implements IDB {
  constructor(
    public id?: number,
    public name?: string,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
