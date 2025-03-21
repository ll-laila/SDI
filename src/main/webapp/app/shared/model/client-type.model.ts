export interface IClientType {
  id?: number;
  type?: string;
  description?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class ClientType implements IClientType {
  constructor(
    public id?: number,
    public type?: string,
    public description?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
