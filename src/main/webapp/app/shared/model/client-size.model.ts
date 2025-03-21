export interface IClientSize {
  id?: number;
  sizeName?: string;
  sizeCode?: string;
  sizeDescription?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class ClientSize implements IClientSize {
  constructor(
    public id?: number,
    public sizeName?: string,
    public sizeCode?: string,
    public sizeDescription?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
