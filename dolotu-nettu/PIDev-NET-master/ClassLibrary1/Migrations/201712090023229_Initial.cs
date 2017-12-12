namespace Data.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
               "formation",
               c => new
               {
                   Id = c.Int(nullable: false, identity: true),
                   type = c.String(unicode: false),
                   adresse = c.String(unicode: false),
                   dateDebut = c.DateTime(precision: 0),
                   dateFin = c.DateTime(precision: 0),
                   NbrePlace = c.Int(nullable: false),
                   description = c.String(unicode: false),
                   prix_unitaire = c.Single(nullable: false),
               })
               .PrimaryKey(t => t.Id);


            CreateTable(
                "participations",
                c => new
                {
                    id = c.Int(nullable: false, identity: true),
                    nombre = c.Int(),
                    date_participation = c.DateTime(precision: 0),
                    user_id = c.Int(),
                    formation_id = c.Int()
                })
                .PrimaryKey(t => t.id)
                .Index(t => t.formation_id)
                .Index(t => t.user_id);
            AddForeignKey("participations", "formation_id", "formation", "id");
            AddForeignKey("participations", "user_id", "user", "id");


        }

        public override void Down()
        {
            DropForeignKey("participations", "user_id", "user");
            DropForeignKey("participations", "formation_id", "formation");
            DropIndex("participations", new[] { "user_id" });
            DropIndex("participations", new[] { "formation_id" });
            DropTable("formation");
            DropTable("participations");
        }
    }
}
